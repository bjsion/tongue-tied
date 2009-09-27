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
package org.tonguetied.audit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.tonguetied.audit.AuditLogRecord.TABLE_AUDIT_LOG_RECORD;
import static org.tonguetied.keywordmanagement.Keyword.TABLE_KEYWORD;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;
import org.tonguetied.audit.AuditLogRecord.Operation;
import org.tonguetied.keywordmanagement.Keyword;
import org.tonguetied.test.common.PersistenceTestBase;

/**
 * Test the persistence of the {@link AuditLogRecord} object.
 * 
 * @author bsion
 *
 */
public class AuditLogRecordPersistenceTest extends PersistenceTestBase
{

    @Test
    public final void simplePersistence()
    {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        assertTrue(tx.isActive());
        
        Keyword keyword = new Keyword();
        keyword.setKeyword("test");
        session.saveOrUpdate(keyword);
        
        AuditLogRecord record = 
            new AuditLogRecord(Operation.insert, keyword, keyword.toString(), null, "username");

        session.saveOrUpdate(record);
        session.close();
        
        session = getSession();
        tx = session.beginTransaction();
        AuditLogRecord reloaded = 
            (AuditLogRecord) session.get(AuditLogRecord.class, record.getId());
        assertEquals(record, reloaded);
        tx.rollback();
        assertTrue(tx.wasRolledBack());
        session.close();
    }
    
    @Test
    public final void testImmutability()
    {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        assertTrue(tx.isActive());
        
        Keyword keyword = new Keyword();
        keyword.setKeyword("test");
        session.saveOrUpdate(keyword);
        
        AuditLogRecord record =
            new AuditLogRecord(Operation.insert, keyword, keyword.toString(), null, "username");

        session.saveOrUpdate(record);
        session.flush();
        tx.commit();
        session.close();
        
        session = getSession();
        tx = session.beginTransaction();
        AuditLogRecord reloaded = 
            (AuditLogRecord) session.get(AuditLogRecord.class, record.getId());
        reloaded.setMessage(Operation.update);
        session.saveOrUpdate(reloaded);
        session.flush();
        tx.commit();
        session.close();
        
        session = getSession();
        tx = session.beginTransaction();
        AuditLogRecord actual = 
            (AuditLogRecord) session.get(AuditLogRecord.class, record.getId());
        assertEquals(Operation.insert, actual.getMessage());
        tx.rollback();
        assertTrue(tx.wasRolledBack());
        session.close();
    }

    @Override
    protected String[] getTableNames()
    {
        return new String[] {TABLE_AUDIT_LOG_RECORD, TABLE_KEYWORD};
    }
}
