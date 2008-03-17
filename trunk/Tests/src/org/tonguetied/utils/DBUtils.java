package org.tonguetied.utils;

import static org.tonguetied.utils.Constants.DB_NAME;
import static org.tonguetied.utils.Constants.DB_PORT;
import static org.tonguetied.utils.Constants.DB_SERVER_PATH;
import static org.tonguetied.utils.Constants.JDBC_DRIVER;
import static org.tonguetied.utils.Constants.JDBC_PASSWORD;
import static org.tonguetied.utils.Constants.JDBC_URL;
import static org.tonguetied.utils.Constants.JDBC_USER_NAME;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.hsqldb.Server;


/**
 * This class implements some utility methods to execute SQL statements in a
 * controllable fashion. The creation of this class was inspired by the ANT sql
 * task where the execution of a SQL is continued even though there is an error.
 * This typically happens when executing a DDL and an ALTER statement fails due
 * to a non exsting table for example. This is not an error situation and can
 * easily be recovered from.
 *
 * @author mforslund
 */
public class DBUtils
{
    private static final String STATEMENT_SHUTDOWN = "SHUTDOWN;";
    private static final Server dbServer = new Server();
    private static final String DDL_FILE = "/schema-export.sql";
    private static String ddlSql = "";
    public static Properties props; 

    private static final Logger log = Logger.getLogger(DBUtils.class);
    
    /**
     *  Create a new database. Drop all tables and re create with no data.
     */
    public static void cleanTables(Connection conn) throws IOException
    {
        DBUtils.executeDDL(conn, getDDLSQL(), false);
    }

    public static void executeDDL(Connection conn, String ddlString, boolean haltOnError)
    {
        try
        {
            Statement statement = conn.createStatement();

            for(String ddlStatement : getStatements(ddlString))
            {
                try
                {
                    statement.execute(ddlStatement);
                }
                catch(SQLException e)
                {
                    log.error("Error executing statement - " + ddlStatement);
                }
            }
            
            if (log.isInfoEnabled())
                log.debug("statement "+ ddlString + " completed successfully");
        }
        catch(SQLException e)
        {
            log.error("Error when executing DDL!", e);
            throw new RuntimeException("Error when executing DDL!", e);
        }
        finally
        {
            if(conn != null)
            {
                try
                {
                    conn.close();
                }
                catch (SQLException e)
                {
                    log.error("Error when closing connection", e);
                }
            }
        }
    }
    
    public static synchronized void startDatabase() throws Exception {
        try {
            // ensure the db server is running
            dbServer.checkRunning(true);
        } 
        catch (RuntimeException re) {
            // If the server is not running then start the server
            if (log.isInfoEnabled()) log.info("Db server has not been started. Attempting to start");
            // load jdbc props
            props = FileUtils.loadProperties("/jdbc.properties");
            
            // initialize DB...
            dbServer.setDatabasePath(0, DB_SERVER_PATH);//+";shutdown=true");
            dbServer.setDatabaseName(0, DB_NAME);
            dbServer.setPort(DB_PORT);
            dbServer.start();
    
            try {
                Class.forName(props.getProperty(JDBC_DRIVER));
            }
            catch (Exception e) {
                System.out.println("ERROR: failed to load HSQLDB JDBC driver.");
                e.printStackTrace();
                return;
            }
    
            // setup JVM termination hook...
//            Runtime.getRuntime().addShutdownHook(new Thread(){
//                @Override
//                public void run() {
//                    if (log.isInfoEnabled()) log.info("shutting down database");
//                    try {
//                        Connection conn =DriverManager.getConnection(
//                                props.getProperty(JDBC_URL), 
//                                props.getProperty(JDBC_USER_NAME),
//                                props.getProperty(JDBC_PASSWORD));
//                        DBUtils.executeDDL(conn, STATEMENT_SHUTDOWN, false);
//                    }
//                    catch (SQLException sqle) {
//                        throw new RuntimeException(sqle);
//                    }
//                    if (dbServer != null)
//                        dbServer.shutdown();
//                }
//            });
        }
    }
    
    public static void stopDatabase() {
        if (log.isInfoEnabled()) log.info("Attempting to stop database");
        if (dbServer != null)
            dbServer.stop();
    }
    
    /**
     *
     * @return String
     */
    private static String getDDLSQL() throws IOException
    {
        if("".equals(ddlSql))
            ddlSql = FileUtils.loadFile(DDL_FILE);

        return ddlSql;
    }
    
    /**
     *
     * @param ddlString
     * @return
     */
    private static String[] getStatements(String ddlString)
    {
        return ddlString.split(";");
    }
}
