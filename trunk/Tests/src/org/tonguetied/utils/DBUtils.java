package org.tonguetied.utils;

import static org.tonguetied.utils.Constants.DB_NAME;
import static org.tonguetied.utils.Constants.DB_PORT;
import static org.tonguetied.utils.Constants.DB_SERVER_PATH;
import static org.tonguetied.utils.Constants.JDBC_DRIVER;
import static org.tonguetied.utils.Constants.JDBC_PASSWORD;
import static org.tonguetied.utils.Constants.JDBC_URL;
import static org.tonguetied.utils.Constants.JDBC_USER_NAME;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.hsqldb.Server;

/**
 * This class implements some utility methods to execute SQL statements in a
 * controllable fashion. The creation of this class was inspired by the ANT sql
 * task where the execution of a SQL is continued even though there is an error.
 * This typically happens when executing a DDL and an ALTER statement fails due
 * to a non existing table for example. This is not an error situation and can
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
     * Create a new database. Drop all tables and re create with no data.
     */
    public static void cleanTables(Connection conn) throws IOException
    {
        DBUtils.executeDDL(conn, getDDLSQL(), false);
    }

    public static void executeDDL(Connection conn, final String ddlString,
            final boolean haltOnError)
    {
        try
        {
            Statement statement = conn.createStatement();
            for (final String ddlStatement : getStatements(ddlString))
            {
                try
                {
                    statement.execute(ddlStatement);
                }
                catch (SQLException e)
                {
                    if (haltOnError)
                        throw e;
                    
                    log.error("Error executing statement - " + ddlStatement);
                }
            }

            if (log.isInfoEnabled())
                log.debug("statement " + ddlString + " completed successfully");
        }
        catch (SQLException e)
        {
            log.error("Error when executing DDL!", e);
            throw new RuntimeException("Error when executing DDL!", e);
        }
        finally
        {
            close(conn);
        }
    }

    /**
     * Gracefully closes a database connection, logging a message if the 
     * connection fails to close.
     * 
     * @param conn the database connection to close
     */
    private static void close(Connection conn)
    {
        if (conn != null)
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

    public static synchronized void startDatabase() throws Exception
    {
        try
        {
            // ensure the db server is running
            if (log.isInfoEnabled())
                log.info("checking if DB is already started");
            dbServer.checkRunning(true);
        }
        catch (RuntimeException re)
        {
            // If the server is not running then start the server
            if (log.isInfoEnabled())
                log.info("Db server has not been started. Attempting to start");
            // load jdbc props
            props = new Properties();
            loadProperties("/jdbc.properties");

            // initialize DB...
            dbServer.setDatabasePath(0, DB_SERVER_PATH);// +";shutdown=true");
            dbServer.setDatabaseName(0, DB_NAME);
            dbServer.setPort(DB_PORT);
            dbServer.start();

            try
            {
                Class.forName(props.getProperty(JDBC_DRIVER));
            }
            catch (ClassNotFoundException cnfe)
            {
                log.error("ERROR: failed to load HSQLDB JDBC driver.");
                cnfe.printStackTrace();
                return;
            }

            // setup JVM termination hook...
            // Runtime.getRuntime().addShutdownHook(new Thread(){
            // @Override
            // public void run() {
            // if (log.isInfoEnabled()) log.info("shutting down database");
            // try {
            // Connection conn =DriverManager.getConnection(
            // props.getProperty(JDBC_URL),
            // props.getProperty(JDBC_USER_NAME),
            // props.getProperty(JDBC_PASSWORD));
            // DBUtils.executeDDL(conn, STATEMENT_SHUTDOWN, false);
            // }
            // catch (SQLException sqle) {
            // throw new RuntimeException(sqle);
            // }
            // if (dbServer != null)
            // dbServer.shutdown();
            // }
            // });
        }
    }

    /**
     * @param fileName the name of the resource to load
     * @throws IOException
     */
    private static void loadProperties(String fileName) throws IOException
    {
        InputStream is = null;
        try
        {
            is = DBUtils.class.getResourceAsStream(fileName);
            props.load(is);
        }
        finally
        {
            IOUtils.closeQuietly(is);
        }
    }

    public static void stopDatabase()
    {
        if (log.isInfoEnabled()) log.info("Attempting to stop database");
        if (dbServer != null) dbServer.stop();
    }

    /**
     * 
     * @return String
     */
    private static String getDDLSQL() throws IOException
    {
        if ("".equals(ddlSql))
        {
            InputStream is = null;
            try
            {
                is = DBUtils.class.getResourceAsStream(DDL_FILE);
                ddlSql = IOUtils.toString(is);
            }
            finally
            {
                IOUtils.closeQuietly(is);
            }
        }

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
