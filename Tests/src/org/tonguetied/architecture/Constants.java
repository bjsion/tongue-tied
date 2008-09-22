package org.tonguetied.architecture;

import java.io.File;

import org.apache.commons.lang.SystemUtils;

/**
 * @author bsion
 *
 */
public interface Constants
{
    static final File BASE_DIR = new File(SystemUtils.getUserDir(), "..");
    static final File APPLICATION_DIR =  new File(BASE_DIR, "Application");
    static final File APPLICATION_CLASSES = new File(APPLICATION_DIR, "classes");
    static final File SERVER_DIR = new File(BASE_DIR, "Server");
    static final File SERVER_CLASSES = new File(SERVER_DIR, "classes");
    
    static final String[] PACKAGE_FILTER = new String[] {
        "au.com.bytecode.*",
        "fmpp*", "freemarker*", "java.*", "javax.*", "org.apache.*", 
        "org.hibernate*", "org.mortbay.*", "org.springframework.*", 
        "org.xml.*"
    };
}
