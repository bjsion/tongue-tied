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
package org.tonguetied.administration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.tonguetied.test.common.Constants.TABLE_SERVER_DATA;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;
import org.tonguetied.test.common.PersistenceTestBase;

/**
 * Test the persistence of the {@link ServerData} object.
 * 
 * @author bsion
 *
 */
public class ServerDataPersistenceTest extends PersistenceTestBase
{
    private static final Date BUILD_DATE = new Date();

    @Test
    public final void simplePersistence()
    {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        assertTrue(tx.isActive());
        
        ServerData serverData = new ServerData("2.0.1", "5684", BUILD_DATE);

        session.saveOrUpdate(serverData);
        session.close();
        
        session = getSession();
        tx = session.beginTransaction();
        ServerData reloaded = 
            (ServerData) session.get(ServerData.class, serverData.getId());
        assertEquals(serverData, reloaded);
        tx.rollback();
        assertTrue(tx.wasRolledBack());
        session.close();
    }
    
    @Test
    public final void testImmutablity()
    {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        assertTrue(tx.isActive());
        
        ServerData serverData = new ServerData("2.0.1", "5684", BUILD_DATE);

        session.saveOrUpdate(serverData);
        session.close();
        
        session = getSession();
        tx = session.beginTransaction();
        ServerData updated = 
            (ServerData) session.get(ServerData.class, serverData.getId());
        updated.setVersion("3.2");
        session.saveOrUpdate(updated);
        session.close();
        
        session = getSession();
        tx = session.beginTransaction();
        ServerData reloaded = 
            (ServerData) session.get(ServerData.class, serverData.getId());
        assertEquals("2.0.1", reloaded.getVersion());
        tx.rollback();
        assertTrue(tx.wasRolledBack());
        session.close();
    }
    
    @Override
    protected String[] getTableNames()
    {
        return new String[] {TABLE_SERVER_DATA};
    }
}
