# Introduction #

This page details how to install and setup Tongue-Tied **Beta 20080923** for use.


# Details #

Download the Tongue-Tied package from [here](http://code.google.com/p/tongue-tied/downloads/list).

## Windows ##
  1. Unzip package to a directory of your choice
  1. Open the file `schema-export.sql` and add the following line to the top of the file:
> > `\c true`
  1. Run the batch file `startDB.bat`. This starts the in memory database
  1. Create the initial schema (you will only need to do this step once). Run the batch file `createDB.bat`
  1. Now start the server. Run the batch file `startServer.bat`. The server will now start up. You should see something like this to indicate that the server is ready:
```
2008-08-02 16:28:29.999::INFO:  Started SelectChannelConnector@0.0.0.0:8080
2008-08-02 16:28:29.389::INFO:  Started SslSocketConnector@0.0.0.0:8443
 INFO [main] (Server.java:378) - Server started, have fun!!
```


Now open [http://localhost:8080/tonguetied](http://localhost:8080/tonguetied) (or what ever IP address you have put the server) and you should come to the login screen. The default administrator username and password is admin / admin.