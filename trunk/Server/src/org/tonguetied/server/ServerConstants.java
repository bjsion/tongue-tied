package org.tonguetied.server;

import java.io.File;

/**
 * This interface defines all the constants used in for starting TongueTied 
 * server using an embedded Jetty server.
 * 
 * @author bsion
 * @see Server
 */
public interface ServerConstants {
    static final String KEY_KEYSTORE_LOC =  "tonguetied.server.keystore.location";
    static final String KEY_KEYSTORE_PASSWORD = "tonguetied.server.keystore.password";
    static final String KEY_LOG_LEVEL = "log.level";
    static final String KEY_REQUEST_LOG_DAYS = "tonguetied.request.log.days";
    static final String KEY_REQUEST_TIMEZONE = "tonguetied.request.log.timezone";
    static final String KEY_SERVER_PORT = "serverPort";
    static final String KEY_CONTEXT_PATH_DEF = "tonguetied.context.path.def";
    static final String KEY_SECURE_SERVER_PORT = "tonguetied.server.secure.port";
    static final String KEY_WORKING_LOC = "tonguetied.server.working.location";
    static final String KEY_JOIN_THREAD_POOL = "tonguetied.join.thread.pool";
    static final String KEY_UNPACK_WAR = "tonguetied.server.unpack.war";
    static final String KEY_TEMP_DIR = "tonguetied.server.temp.dir";
    static final String KEY_REQUEST_LOG_FILE = "tonguetied.server.request.log.file";
    static final String KEY_MAX_THREADS = "tonguetied.server.max.threads";
    static final String KEY_MIN_THREADS = "tonguetied.server.min.threads";
    static final String KEY_USE_FILE_MAPPED_BUFFER = 
    	"tonguetied.server.useFileMappedBuffer";
    
    /**
     * The default location to look for the war file or an extracted war file.
     */
    static final String DEFAULT_WORKING_LOC = System.getProperty("user.dir");
    
    /**
     * The default location where the server will extract files and where the
     * embedded server will operate.
     */
    static final File DEFAULT_TEMP_DIR = 
        new File(System.getProperty("user.dir"), "tonguetied");
    
    /**
     * The default name and location of the keystore file.
     */
    static final String DEFAULT_KEYSTORE_LOC = "keystore";
    
    /**
     * The default password of the keystore.
     */
    static final String DEFAULT_KEYSTORE_PASSWORD = "tonguetied";
    
    /**
     * The default length of time to keep the request log.
     */
    static final String DEFAULT_REQUEST_LOG_DAYS = "7";
    
    /**
     * The default timezone for the request log.
     */
    static final String DEFAULT_REQUEST_LOG_TIMEZONE = "GMT";
    
    /**
     * The default http server port.
     */
    static final String DEFAULT_SERVER_PORT = "8080";
    
    /**
     * The default ssl server port.
     */
    static final String DEFAULT_SECURE_SERVER_PORT = "443";
    
    /**
     * The default flag indicating if the server should join the thread pool at
     * start up.
     */
    static final String DEFAULT_JOIN_THREAD_POOL = "false";
    
    /**
     * The default flag indicating if the server should unpack web archives on 
     * start up.
     */
    static final String DEFAULT_UNPACK_WAR = "false";
    
    /**
     * The default number of maximum thread pool size.
     */
    static final String DEFAULT_MAX_THREADS = "100";
    
    /**
     * The default number of maximum thread pool size.
     */
    static final String DEFAULT_MIN_THREADS = "10";
    
    /**
     * @see <a href="http://docs.codehaus.org/display/JETTY/Files+locked+on+Windows">Files locked on windows</a>
     * @see org.mortbay.jetty.servlet.DefaultServlet
     */
    static final String USE_FILE_MAPPED_BUFFER = 
                "org.mortbay.jetty.servlet.Default.useFileMappedBuffer";
        
    /**
     * The default name of the properties file used to configure the embedded
     * servers
     */
    static final String DEFAULT_PROP_FILE = "embeddedServer.properties";
}
