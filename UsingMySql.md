# Introduction #

TongueTied requires a few steps to be performed in order to connect to a MySQL database.


# Details #

  1. Download the MySQL Driver from [here](http://dev.mysql.com/downloads/connector/j/5.1.html).
  1. Extract the web archive file, if using TongueTied with an embedded server start the server by running the batch file `startServer.bat`.
  1. After the server has started, stop the server.
  1. Copy the jdbc driver named something like mysql-connector-java-X.X.X-bin.jar to `<TONGUETIED_HOME>/tonguetied/webapp/WEB-INF/lib`
  1. Open the file `<TONGUETIED_HOME>/tonguetied/webapp/WEB-INF/jdbc.properties`
  1. Set your MySQL Connection to something like the following:
```
jdbc.driverClassName=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/tonguetied
jdbc.username=root
jdbc.password=password
hibernate.dialect=org.hibernate.dialect.MySQLDialect
```
  1. Restart the server.