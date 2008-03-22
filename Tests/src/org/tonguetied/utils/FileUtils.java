package org.tonguetied.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

/**
 * This class implements some utility methods for file reading, writing and 
 * manipulation. 
 * 
 * @author bsion
 *
 */
public class FileUtils
{
    /**
     * Read a file from the classpath or file system and return the contents as
     * a String. The order of execution for locating the file is:
     * <ul>
     * <li>Attempt to load the file from the classpath</li>
     * <li>if no file matching name is found, then attempt load the file from
     * a file system.</li>
     * </ul>
     * 
     * @param resourceName the name of the file to load
     * @return the contents of a file as a <code>String</code>
     * @throws IOException if the file fails to load
     * @throws IllegalArgumentException if <code>fileName</code> is 
     * <code>null</code>
     * @see #loadFile(InputStream)
     */
    public static String loadFile(String resourceName) throws IOException {
        if (StringUtils.isEmpty(resourceName)) {
            throw new IllegalArgumentException("resource name cannot be null");
        }
        InputStream resource = getInputStream(resourceName);
        
        return loadFile(resource);
    }

    /**
     * Get the contents of a {@link File} as a String. Convience method to 
     * {@link #loadFile(InputStream)}  
     * 
     * @param file the name of the file to load
     * @return the contents of a file as a <code>String</code>
     * @throws IOException if the file fails to load
     * @throws IllegalArgumentException if <code>file</code> does not exist or 
     * is <code>null</code>
     * @see #loadFile(InputStream)
     */
    public static String loadFile(File file) throws IOException {
        if ((file == null) || (!file.exists())) {
            throw new IllegalArgumentException("File cannot be null and " +
                        "must exist");
        }
        
        InputStream resource = new FileInputStream(file);
        return loadFile(resource);
    }
    
    /**
     * Get the contents of a {@link InputStream} as a String. 
     * 
     * @param resource the name of the file to load
     * @return the contents of a file as a <code>String</code>
     * @throws IOException if the file fails to load
     */
    public static String loadFile(InputStream resource) throws IOException {
        StringBuilder sb = new StringBuilder();
        
        BufferedReader in = null; 
        try 
        {
            in = new BufferedReader(new InputStreamReader(resource));
            
            String str;
            while ((str = in.readLine()) != null) 
                sb.append(str);
        } 
        finally {
            if (in != null)
                in.close();
            if (resource != null)
                resource.close();
        }
        
        return sb.toString();
    }
    
    /**
     * Load the contents of a resource file into a {@link Properties} object.
     * This method assumes the resource file is in the same class path as this
     * {@linkplain FileUtils} object.
     * 
     * @param fileName the name of resource file.
     * @return the resources loaded from the file.
     * @throws IOException if an error occurs reading the file.
     * @throws IllegalArgumentException if <code>fileName</code> is 
     * <code>null</code>
     */
    public static Properties loadProperties(String fileName) throws IOException {
        if (StringUtils.isEmpty(fileName)) {
            throw new IllegalArgumentException("resource name cannot be null");
        }
        InputStream resource = getInputStream(fileName);
        
        return loadProperties(resource);
    }

    public static Properties loadProperties(File file) throws IOException {
        if ((file == null) || (!file.exists())) {
            throw new IllegalArgumentException("File cannot be null and " +
                        "must exist");
        }
        
        InputStream resource = new FileInputStream(file);
        return loadProperties(resource);
    }
    
    public static Properties loadProperties(InputStream resource) throws IOException {
        Properties props = new Properties();
        try {
            // static reference to class. if use of this.getClass may result in 
            // problems if a class from another package extends this class
            props.load(resource);
        }
        finally {
            if (resource != null) 
                resource.close();
        }
        
        return props;
    }
    
    /**
     * @param resourceName
     * @return
     * @throws FileNotFoundException
     */
    private static InputStream getInputStream(String resourceName) throws FileNotFoundException {
        // static reference to class. if use of this.getClass may result in 
        // problems if a class from another package extends this class
        InputStream resource = 
            FileUtils.class.getResourceAsStream(resourceName);
        if (resource == null) {
            File file = new File(resourceName);
            resource = new FileInputStream(file);
        }
        return resource;
    }
}
