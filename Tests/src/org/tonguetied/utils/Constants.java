/*
 * Copyright 2008 The Tongue-Tied Authors
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not 
 * use this file except in compliance with the License. You may obtain a copy 
 * of the License at
 *  
 * http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the 
 * License for the specific language governing permissions and limitations 
 * under the License. 
 */
package org.tonguetied.utils;

import java.io.File;


/**
 * File containing global constants used in the testing module. 
 * 
 * @author bsion
 *
 */
public interface Constants
{
    /**
     * The key for the hibernate sql dialect.
     */
    public static final String HIBERNATE_DIALECT = "hibernate.dialect";
    /**
     * The key for the jdbc driver class name.
     */
    public static final String JDBC_DRIVER = "jdbc.driverClassName";
    /**
     * The key for the jdbc url. This is the address of the database server. 
     */
    public static final String JDBC_URL = "jdbc.url";
    /**
     * The key for the database user name.
     */
    public static final String JDBC_USER_NAME = "jdbc.username";
    /**
     * The key for the database user's password.
     */
    public static final String JDBC_PASSWORD = "jdbc.password";
    
    /**
     * The physical location of the database.
     */
    public static final String DB_SERVER_PATH = "data"+File.separator+"tonguetied";
    
    /**
     * The database name. 
     */
    public static final String DB_NAME = "tonguetied";
    
    /**
     * The database port number
     */
    public static final int DB_PORT = 9011;
}
