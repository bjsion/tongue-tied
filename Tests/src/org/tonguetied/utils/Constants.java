package org.tonguetied.utils;

import java.io.File;


/**
 * File containing global constants used in the testing module. 
 * 
 * @author bsion
 *
 */
public interface Constants {
    /**
     * The key for the jdbc driver class name.
     */
    public static final String JDBC_DRIVER = "jdbc.driverClassName";
    /**
     * The key for the jdbc url. This is the address of the database server. 
     */
    public static final String JDBC_URL = "jdbc.url";
    /**
     * The key for the database user name.
     */
    public static final String JDBC_USER_NAME = "jdbc.username";
    /**
     * The key for the database user's password.
     */
    public static final String JDBC_PASSWORD = "jdbc.password";
    
    /**
     * The physical location of the database.
     */
    public static final String DB_SERVER_PATH = "data"+File.separator+"tonguetied";
    
    /**
     * The database name. 
     */
    public static final String DB_NAME = "tonguetied";
    
    /**
     * The database port number
     */
    public static final int DB_PORT = 9001;
}
