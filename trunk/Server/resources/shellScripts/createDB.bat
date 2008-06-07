@rem Creating Tongue-Tied DB schema
@java -Dsqlfile.charset=UTF-8 -jar libs/hsqldb.jar --rcfile ./sqltool.rc tonguetied schema-export.sql

@rem Populating default data for Tongue-Tied
@java -Dsqlfile.charset=UTF-8 -jar libs/hsqldb.jar --rcfile ./sqltool.rc tonguetied initial-data.sql