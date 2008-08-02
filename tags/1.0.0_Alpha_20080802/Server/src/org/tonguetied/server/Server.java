package org.tonguetied.server;

import static org.tonguetied.server.ServerConstants.DEFAULT_JOIN_THREAD_POOL;
import static org.tonguetied.server.ServerConstants.DEFAULT_KEYSTORE_PASSWORD;
import static org.tonguetied.server.ServerConstants.DEFAULT_MAX_THREADS;
import static org.tonguetied.server.ServerConstants.DEFAULT_MIN_THREADS;
import static org.tonguetied.server.ServerConstants.DEFAULT_PROP_FILE;
import static org.tonguetied.server.ServerConstants.DEFAULT_REQUEST_LOG_DAYS;
import static org.tonguetied.server.ServerConstants.DEFAULT_REQUEST_LOG_TIMEZONE;
import static org.tonguetied.server.ServerConstants.DEFAULT_SECURE_SERVER_PORT;
import static org.tonguetied.server.ServerConstants.DEFAULT_SERVER_PORT;
import static org.tonguetied.server.ServerConstants.DEFAULT_TEMP_DIR;
import static org.tonguetied.server.ServerConstants.DEFAULT_UNPACK_WAR;
import static org.tonguetied.server.ServerConstants.DEFAULT_WORKING_LOC;
import static org.tonguetied.server.ServerConstants.KEY_CONTEXT_PATH_DEF;
import static org.tonguetied.server.ServerConstants.KEY_JOIN_THREAD_POOL;
import static org.tonguetied.server.ServerConstants.KEY_KEYSTORE_LOC;
import static org.tonguetied.server.ServerConstants.KEY_KEYSTORE_PASSWORD;
import static org.tonguetied.server.ServerConstants.KEY_LOG_LEVEL;
import static org.tonguetied.server.ServerConstants.KEY_MAX_THREADS;
import static org.tonguetied.server.ServerConstants.KEY_MIN_THREADS;
import static org.tonguetied.server.ServerConstants.KEY_REQUEST_LOG_DAYS;
import static org.tonguetied.server.ServerConstants.KEY_REQUEST_LOG_FILE;
import static org.tonguetied.server.ServerConstants.KEY_REQUEST_TIMEZONE;
import static org.tonguetied.server.ServerConstants.KEY_SECURE_SERVER_PORT;
import static org.tonguetied.server.ServerConstants.KEY_SERVER_PORT;
import static org.tonguetied.server.ServerConstants.KEY_TEMP_DIR;
import static org.tonguetied.server.ServerConstants.KEY_UNPACK_WAR;
import static org.tonguetied.server.ServerConstants.KEY_USE_FILE_MAPPED_BUFFER;
import static org.tonguetied.server.ServerConstants.KEY_WORKING_LOC;
import static org.tonguetied.server.ServerConstants.SERVER_CONFIGURATION_CLASSES;
import static org.tonguetied.server.ServerConstants.USE_FILE_MAPPED_BUFFER;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.Logger;
import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Handler;
import org.mortbay.jetty.HandlerContainer;
import org.mortbay.jetty.NCSARequestLog;
import org.mortbay.jetty.RequestLog;
import org.mortbay.jetty.deployer.WebAppDeployer;
import org.mortbay.jetty.handler.ContextHandlerCollection;
import org.mortbay.jetty.handler.DefaultHandler;
import org.mortbay.jetty.handler.HandlerCollection;
import org.mortbay.jetty.handler.RequestLogHandler;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.security.SslSocketConnector;
import org.mortbay.jetty.webapp.WebAppContext;
import org.mortbay.thread.BoundedThreadPool;

/**
 * This class implements a simple HTTP Server using Jetty, providing a HTTP 
 * listener and handle incoming requests. This class will start an embedded 
 * jetty server initialized from a properties file. The default is 
 * <code>embeddedServer.properties</code> or an alternative can be passed in at
 * runtime. Default values are derived from {@link ServerConstants}. By default
 * the server does not join the thread pool. To enable this behaviour set 
 * the {@link ServerConstants#KEY_JOIN_THREAD_POOL} in the server properties
 * file.
 * 
 *  The folling options are available for the command line:
 *  <ul>
 *  <li>-p &lt;propertyFileName&gt; the name and location of the properties file
 *  to use.</li>
 *  <li>-help, -? displays the help message.</li>
 *  </ul>
 *
 * @author mforslund
 * @see ServerConstants
 */
public class Server
{
    private static org.mortbay.jetty.Server embeddedServer;
    private static long startTime = System.currentTimeMillis();

    private boolean initialized;
    private Properties properties;
    private SslSocketConnector sslSocketConnector = new SslSocketConnector();
    
    private static final DateFormat DATE_FORMAT = 
        new SimpleDateFormat("yyyy-MM-dd");
    
    private static String propsFile = DEFAULT_PROP_FILE;

    private static final Logger log = Logger.getLogger(Server.class);

    /**
     * Create a new instance of the embedded web server.
     * 
     * @param fileName the name and / or location of the properties file to
     * load 
     * @throws IOException if there is a problem reading the server properties
     * file
     */
    public Server(final String fileName) throws IOException {
        properties = loadProperties(fileName);
        
        // Set debug level logging if specified
        if ("DEBUG".equalsIgnoreCase(getLogLevel())) {
            System.setProperty("DEBUG", "true");
        }
        
        if (log.isInfoEnabled()) 
            log.info("Creating new Server instance with properties:\n" + 
                    properties);
    }

    /**
     * This method returns diagnostics information from the server. 
     *  
     * @return the server statistics
     */
    public static Map<String, String> getServerInfo()
    {
        Map<String, String> info = new HashMap<String, String>();
        info.put("serverVersion", org.mortbay.jetty.Server.getVersion());
        long uptime = System.currentTimeMillis() - startTime;
        info.put("uptime", Long.toString(uptime));
  
        return info;
    }
    
    
    /**
     * Configure the embedded jetty server. This method sets all the start up 
     * parameters for the jetty server based on the values in the 
     * <code>embeddedServer.properties</code>. If no values are found in this 
     * file, then the default values are used.
     * 
     * @exception Exception if the embedded server fails to initialize
     * @see ServerConstants
     */
    protected void initializeServer() throws Exception {
        // Create the embeddedServer
        embeddedServer = new org.mortbay.jetty.Server();
        
        Connector connector = new SelectChannelConnector();
        connector.setPort(getPort());
        
        sslSocketConnector.setPort(getSecurePort());
        sslSocketConnector.setKeystore(getKeystoreLocation());
        sslSocketConnector.setKeyPassword(getKeystorePassword());
        sslSocketConnector.setPassword(getKeystorePassword());

        embeddedServer.addConnector(connector);
        embeddedServer.addConnector(sslSocketConnector);
        
        BoundedThreadPool threadPool = new BoundedThreadPool();
        threadPool.setMaxThreads(getMaxThreads());
        threadPool.setMinThreads(getMinThreads());
        embeddedServer.setThreadPool(threadPool);
        
        HandlerCollection handlers = new HandlerCollection();
        ContextHandlerCollection contexts = new ContextHandlerCollection();
        RequestLogHandler requestLogHandler = new RequestLogHandler();
        handlers.setHandlers(
                new Handler[]{contexts,
                              new DefaultHandler(),
                              requestLogHandler});
        
        embeddedServer.setHandler(handlers);
        
        requestLogHandler.setRequestLog(createRequestLog());
        
        WebAppDeployer deployer = createWebAppDeployer(contexts);
        // We need to start the deployer in order to create a WebAppContext
        deployer.start();
        
        configureWebAppContexts(deployer.getContexts());
        
        embeddedServer.addLifeCycle(deployer);
        embeddedServer.setStopAtShutdown(true);
        embeddedServer.setSendServerVersion(true);

        initialized = true;
    }

    /**
     * Factory method to create a new initialized instance of a 
     * {@link WebAppDeployer}.
     *  
     * @param contexts the list of handlers to set for this 
     * {@link WebAppDeployer}
     * @return the newly created {@link WebAppDeployer}
     */
    private WebAppDeployer createWebAppDeployer(ContextHandlerCollection contexts)
    {
        WebAppDeployer deployer = new WebAppDeployer();
        deployer.setContexts(contexts);
        deployer.setWebAppDir(getWorkingLocation());
        deployer.setExtract(unpackWebArchive());
        deployer.setConfigurationClasses(SERVER_CONFIGURATION_CLASSES);
        deployer.setParentLoaderPriority(false);
        deployer.setAllowDuplicates(false);
        
        return deployer;
    }

    /**
     * Factory method to create a new initialized instance of a 
     * {@link RequestLog}. 
     * 
     * @return the newly created {@link RequestLog}
     */
    private RequestLog createRequestLog()
    {
        final String logDate = DATE_FORMAT.format(new Date());
        String logFile = properties.getProperty(KEY_REQUEST_LOG_FILE);
        logFile = logFile.replaceAll("X", logDate);
        
        NCSARequestLog requestLog = new NCSARequestLog(logFile);
        requestLog.setRetainDays(getRequestLogRetainDays());
        requestLog.setAppend(true);
        requestLog.setExtended(false);
        requestLog.setLogTimeZone(getRequestLogTimeZone());
        return requestLog;
    }

    /**
     * Configure the WebAppContext component of the embedded server. This 
     * method does the following:
     * <ul>
     *  <li>Set the context path of the url.</li>
     *  <li>Set the temp directory where the server will work from.</li>
     * </ul>
     * 
     * @param contexts the list of handlers for this web server
     * 
     * @see ServerConstants#KEY_CONTEXT_PATH_DEF
     * @see ServerConstants#KEY_TEMP_DIR
     */
    private void configureWebAppContexts(HandlerContainer contexts)
    {
        String contextPathDef[] = getContextPathDef();
        
            WebAppContext context = 
                (WebAppContext)contexts.getChildHandlerByClass(WebAppContext.class);
        context.setTempDirectory(getTempDirectory());            
        
        Map<String, String> initParams = new HashMap<String, String>();
        // see http://docs.codehaus.org/display/JETTY/Files+locked+on+Windows
        // for reasons on why we do this
        if (useFileMappedBuffer() != null)
        	initParams.put(USE_FILE_MAPPED_BUFFER, useFileMappedBuffer());
        context.setInitParams(initParams);
        
        if(contextPathDef != null) {
            // This is one way of doing it, another way would be to use another
            // request forwarding solution like Apache but that would be a but
            // overkill... 
            if (contextPathDef[0].equals(context.getContextPath())) {
                context.setContextPath(contextPathDef[1]);
            }
        }
    }

    private String[] getContextPathDef() 
    {
        String contextPathDef = properties.getProperty(KEY_CONTEXT_PATH_DEF);           
        return contextPathDef == null? null : contextPathDef.split(",");
    }
    
    private int getPort()
    {
        return Integer.parseInt(
                properties.getProperty(KEY_SERVER_PORT, DEFAULT_SERVER_PORT));
    }

    private int getSecurePort()
    {
        return Integer.parseInt(
                properties.getProperty(KEY_SECURE_SERVER_PORT,
                        DEFAULT_SECURE_SERVER_PORT));
    }

    private String getKeystorePassword()
    {
        return properties.getProperty(KEY_KEYSTORE_PASSWORD,
                DEFAULT_KEYSTORE_PASSWORD);
    }
    
    private String getWorkingLocation()
    {
        return properties.getProperty(KEY_WORKING_LOC, DEFAULT_WORKING_LOC);
    }
    
    private File getTempDirectory() {
        String tempDirStr =  properties.getProperty(KEY_TEMP_DIR); 
        File tempDir = (tempDirStr == null || "".equals(tempDirStr))? 
                DEFAULT_TEMP_DIR: new File(tempDirStr);

        return tempDir;
    }
    
    private String getLogLevel()
    {
        return properties.getProperty(KEY_LOG_LEVEL);
    }

    private String getKeystoreLocation()
    {
        return properties.getProperty(KEY_KEYSTORE_LOC,
                sslSocketConnector.getKeystore());
    }
    
    private int getRequestLogRetainDays() {
        return Integer.parseInt(
                properties.getProperty(KEY_REQUEST_LOG_DAYS,
                        DEFAULT_REQUEST_LOG_DAYS));
    }
    
    private String getRequestLogTimeZone() {
        return properties.getProperty(KEY_REQUEST_TIMEZONE,
                DEFAULT_REQUEST_LOG_TIMEZONE);
    }
    
    private boolean isJoiningThreadPool() {
        return Boolean.parseBoolean(
                properties.getProperty(KEY_JOIN_THREAD_POOL, 
                        DEFAULT_JOIN_THREAD_POOL));
    }
    
    private boolean unpackWebArchive() {
        return Boolean.parseBoolean(
                properties.getProperty(KEY_UNPACK_WAR, 
                        DEFAULT_UNPACK_WAR));
    }
    
    private int getMaxThreads() 
    {
        return Integer.parseInt(
                        properties.getProperty(KEY_MAX_THREADS, DEFAULT_MAX_THREADS));
    }
    
    private int getMinThreads() 
    {
        return Integer.parseInt(
                        properties.getProperty(KEY_MIN_THREADS, DEFAULT_MIN_THREADS));
    }
    
    private String useFileMappedBuffer() {
    	return properties.getProperty(KEY_USE_FILE_MAPPED_BUFFER);
    }
    
    /**
     * Starts the embedded Jetty server.
     * 
     * @throws Exception if the embedded server has not been initialized
     */
    protected void startServer() throws Exception
    {
        if(!initialized)
            throw new RuntimeException("Server not initialized, please initialize!!");

        embeddedServer.start();
        // Only join the thread pool if the user explicitly states
        if (isJoiningThreadPool())
            embeddedServer.join();

        if (log.isInfoEnabled()) log.info("Server started, have fun!!");
    }


    public void start()
    {
        if (embeddedServer == null || !embeddedServer.isRunning()) {
            try {
                initializeServer();
                startServer();
            }
            catch (Exception e)
            {
                log.error("Cannot start server, please check config!!", e);
                System.exit(-1);
            }
        }
        else {
            log.warn("server is already running. Cannot start new instance" +
                        " of same server.");
        }
    }

    /**
     * Stops the HTTP server and terminates the thread.
     */
    public void shutdown()
    {
        try {
            embeddedServer.stop();
        }
        catch (Exception e)
        {
            log.error("Error when stopping the server!!", e);
            System.exit(-1);
        }
    }

    /**
     * Entry point for the embedded server. Starts the embedded web server, 
     * deploying the war file. The server settings are configured in the
     * <code>embeddedServer.properties</code> by default. If another properties
     * file is to be used specify the location as an argument with the
     * <code>-p</code> option.
     *
     * @param args the list of arguments to start the application
     */
    public static void main(String[] args) throws Exception
    {
        readArguments(args);
        
        Server server = new Server(propsFile);
        server.start();
    }

    /**
     * Read and process command line arguments when the server is run.
     * 
     * @param args the arguments to process
     * @throws IllegalArgumentException if the arguments fail to be initialized
     * correctly
     */
    private static void readArguments(String[] args) throws IllegalArgumentException
    {
        Option help = new Option("help", "print this message");
        OptionBuilder.withArgName("file");
        OptionBuilder.hasArg();
        OptionBuilder.withDescription(
                "the name and location of the properties file to use");
        Option propertiesFile = OptionBuilder.create("p");
        
        Options options = new Options();
        options.addOption(help);
        options.addOption(propertiesFile);
        
        CommandLineParser parser = new GnuParser();
        try {
            // parse the command line arguments
            CommandLine line = parser.parse(options, args);
            
            // has the properties file argument been passed?
            if (line.hasOption("p")) {
                // initialise the member variable
                propsFile = line.getOptionValue("p");
            }
            if (line.hasOption("help")) {
                printHelp(options, 0);
            }
        }
        catch(ParseException pe) {
            // oops, something went wrong
            System.err.println("Parsing failed. Reason: " + pe.getMessage());
            printHelp(options, 1);
        }
    }

    /**
     * Print the help message and exit the system.
     * 
     * @param options used to create the help message.
     * @param status exit status
     * 
     * @see System#exit(int)
     */
    private static void printHelp(Options options, int status)
    {
        // automatically generate the help statement
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("Server", options, true);
        System.exit(status);
    }
    
    /**
     * Load the contents of a resource file into a {@link Properties} object.
     * This method assumes the resource file is in the same class path as this
     * {@linkplain Server} object. The order of execution for locating the file
     * is:
     * <ul>
     * <li>Attempt to load the file from the classpath</li>
     * <li>if no file matching name is found, then attempt load the file from
     * the file system.</li>
     * </ul>
     * 
     * @param fileName the name of resource file.
     * @return the resources loaded from the file.
     * @throws IOException if an error occurs reading the file.
     * @throws IllegalArgumentException if <code>fileName</code> is 
     * <code>null</code>
     */
    private static Properties loadProperties(String fileName) throws IOException {
        if (fileName == null) {
            throw new IllegalArgumentException("resource name cannot be null");
        }
        Properties props = new Properties();
        InputStream is = null; 
        try {
            // static reference to class. if use of this.getClass may result in 
            // problems if a class from another package extends this class
            is = Server.class.getClassLoader().getResourceAsStream(fileName);
            if (is == null) {
                File file = new File(fileName);
                is = new FileInputStream(file);
            }
            props.load(is);
        }
        finally {
            if (is != null) 
                is.close();
        }
        
        return props;
    }
}
