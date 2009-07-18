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
package org.tonguetied.utils.database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.tonguetied.utils.database.Constants.DB_NAME;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test starting and stopping the embedded database in a controlled fashion
 * 
 * @author bsion
 *
 */
public class EmbeddedDatabaseServerTest
{
    private static Properties properties = new Properties();
    
    @BeforeClass
    public static void initialize() throws Exception
    {
        InputStream is = null;
        try
        {
            is = EmbeddedDatabaseServerTest.class.getResourceAsStream("/jdbc.properties");
            properties.load(is);
        }
        finally
        {
            IOUtils.closeQuietly(is);
        }
    }
    
    @Before
    public void setUp() throws Exception
    {
        if (EmbeddedDatabaseServer.getServer() != null)
        {
            if (EmbeddedDatabaseServer.isRunning())
            {
                EmbeddedDatabaseServer.stopDatabase();
            }
        }
    }
    
    /**
     * Test method for {@link org.tonguetied.utils.database.EmbeddedDatabaseServer#startDatabase(Properties)}.
     */
    @Test
    public final void testStartStopDatabase()
    {
        // Ensure the server is not running
//        EmbeddedDatabaseServer.getServer().checkRunning(false);
        assertFalse(EmbeddedDatabaseServer.isRunning());
        
        EmbeddedDatabaseServer.startDatabase(properties);
        // Ensure the server is running
        EmbeddedDatabaseServer.getServer().checkRunning(true);
        assertTrue(EmbeddedDatabaseServer.isRunning());
        assertEquals(9011, EmbeddedDatabaseServer.getServer().getPort());
        assertEquals(DB_NAME, EmbeddedDatabaseServer.getServer().getDatabaseName(0, false));
        assertEquals("data"+File.separator+DB_NAME, EmbeddedDatabaseServer.getServer().getDatabasePath(0, false));

        // Running a second time should do nothing
        EmbeddedDatabaseServer.startDatabase(properties);
        // Ensure the server is running
        EmbeddedDatabaseServer.getServer().checkRunning(true);
        assertTrue(EmbeddedDatabaseServer.isRunning());
        
        EmbeddedDatabaseServer.shutdownDatabase();
        // Ensure the server is not running
        assertNull(EmbeddedDatabaseServer.getServer());
        assertFalse(EmbeddedDatabaseServer.isRunning());
    }
    /**
     * Test method for {@link org.tonguetied.utils.database.EmbeddedDatabaseServer#startDatabase(Properties)}.
     */
    @Test(expected=IllegalArgumentException.class)
    public final void testStartDatabaseWithNullProperties()
    {
        // Ensure the server is not running
        assertNull(EmbeddedDatabaseServer.getServer());
        assertFalse(EmbeddedDatabaseServer.isRunning());
        
        EmbeddedDatabaseServer.startDatabase(null);
    }
    
    @Test
    public final void testIsRunningBeforeServerStart()
    {
        assertFalse(EmbeddedDatabaseServer.isRunning());
    }
    @Test
    public final void testIsEmbeddableWithNullDialect()
    {
        assertFalse(EmbeddedDatabaseServer.isEmbeddable(null));
    }
    
    @Test
    public final void testIsEmbeddableWithStandaloneDialect()
    {
        assertFalse(EmbeddedDatabaseServer.isEmbeddable("org.hibernate.dialect.MySQLDialect"));
    }
    
    @Test
    public final void testIsEmbeddableWithUnknownDialect()
    {
        assertFalse(EmbeddedDatabaseServer.isEmbeddable("unknown"));
    }
    
    @Test
    public final void testIsEmbeddableWithEmbeddableDialect()
    {
        assertTrue(EmbeddedDatabaseServer.isEmbeddable("org.hibernate.dialect.HSQLDialect"));
    }
}
