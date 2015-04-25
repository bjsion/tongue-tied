# Introduction #

TongueTied requires a few steps to be performed in order to connect to a PostgreSQL database.


# Details #

  1. Extract the web archive file, if using TongueTied with an embedded server start the server by running the batch file `startServer.bat` or `startServer.sh`.
  1. After the server has started, stop the server.
  1. Open the file `<TONGUETIED_HOME>/tonguetied/webapp/WEB-INF/jdbc.properties`
  1. Set your PostgreSQL Connection to something like the following:
```
jdbc.driverClassName=org.postgresql.Driver
jdbc.port=5432
jdbc.name=tonguetied
jdbc.url=jdbc:postgresql://localhost:5432/tonguetied
jdbc.username=postgres
jdbc.password=password
hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```
  1. Restart the server.