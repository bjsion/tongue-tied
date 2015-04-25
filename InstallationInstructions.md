# Introduction #

This page details how to install and setup Tongue-Tied for use.

# Prerequisites #
Java 5 or above.

# Details #

Download the Tongue-Tied package from [here](http://code.google.com/p/tongue-tied/downloads/list).

## Windows & Linux ##
  1. Unpack the archive to a directory of your choice
  1. Choose a database (currently supported: HSQLDB, MySQL, PostgreSQL). Please note, that in order for the TongueTied application to create the underlying database, the DB user it uses to connect to the database must have create table and delete table privileges. (These can be turned off after TongueTied has started for the first time)
    * If you are using a MySQL database follow the instructions in the [Using a MySQL Database](UsingMySql.md).
    * If you are using a PostgreSQL database follow the instructions in the [Using a PostgreSQL Database](UsingPostreSQL.md).
    * If you are evaluating TongueTied, you can skip this step and use the embedded database.
  1. To configure the embedded Jetty server see the instructions [here](ConfiguringEmbeddedWebServer.md)
  1. Now start the server. For windows users, run the batch file `startServer.bat`. For `*`nix users run the script `startServer.sh`. The server will now start up. You should see something like this to indicate that the server is ready:
```
2008-08-02 16:28:29.999::INFO:  Started SelectChannelConnector@0.0.0.0:8080
2008-08-02 16:28:29.389::INFO:  Started SslSocketConnector@0.0.0.0:8443
 INFO [main] (Server.java:378) - Server started, have fun!!
```


Now open http://localhost:8080/tonguetied (or what ever IP address you have put the server) and you should come to the login screen. The default administrator username and password is admin / admin.

## War file ##
  1. Before deploying check that your web server or application server is compatible with the Servlet spec 2.5 and JSP spec 2.1.
    * For Jetty ...
    * For Tomcat see http://tomcat.apache.org/whichversion.html
    * For JBoss Application Server ...
    * For Resin see ...
  1. This step is optional, but you may wish to rename the war file from TongueTied\_X\_X\_X-yyyyMMdd.war to something simpler, like TongueTied.war
  1. Drop the war file into the web servers apps directory.
  1. Choose a database (see the Windows & Linux for instructions on how to setup the database).
  1. Start your web server if not already started. You will need to restart the TongueTied application in order for any database configuration changes to take effect.
  1. Open the TongueTied URL for example http://localhost:8080/tonguetied.



**Next**: [Getting started](GettingStarted.md)