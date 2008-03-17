package org.tonguetied.domain;

import static org.junit.Assert.assertEquals;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

/**
 * @author bsion
 *
 */
public class AuditLogRecordPersistenceTest extends PersistenceTestBase {

    @Test
    public final void simplePersistence() {
        Session session;
        Transaction tx;
        
        session = getSession();
        tx = session.beginTransaction();
        
        Keyword keyword = new Keyword();
        keyword.setKeyword("test");
        session.saveOrUpdate(keyword);
        
        AuditLogRecord record = new AuditLogRecord("new", keyword, null);

        session.saveOrUpdate(record);
        session.close();
        
        session = getSession();
        tx = session.beginTransaction();
        AuditLogRecord reloaded = 
            (AuditLogRecord) session.get(AuditLogRecord.class, record.getId());
        assertEquals(record, reloaded);
        tx.rollback();
        session.close();
    }
    
    @Test
    public final void persistWithUser() {
        Session session;
        Transaction tx;
        
        session = getSession();
        tx = session.beginTransaction();
        
        User user = new User("username", "password", "firstName", "lastName", "test@test.com", true, true, true, true);
        session.saveOrUpdate(user);
        
        Keyword keyword = new Keyword();
        keyword.setKeyword("test");
        session.saveOrUpdate(keyword);
        
        AuditLogRecord record = new AuditLogRecord("new", keyword, user);

        session.saveOrUpdate(record);
        session.close();
        
        session = getSession();
        tx = session.beginTransaction();
        AuditLogRecord reloaded = 
            (AuditLogRecord) session.get(AuditLogRecord.class, record.getId());
        assertEquals(record, reloaded);
        tx.rollback();
        session.close();
    }
}
