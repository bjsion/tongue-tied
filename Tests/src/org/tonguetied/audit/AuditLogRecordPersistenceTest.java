package org.tonguetied.audit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;
import org.tonguetied.keywordmanagement.Keyword;
import org.tonguetied.test.common.PersistenceTestBase;

/**
 * @author bsion
 *
 */
public class AuditLogRecordPersistenceTest extends PersistenceTestBase {

    @Test
    public final void simplePersistence() {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        
        Keyword keyword = new Keyword();
        keyword.setKeyword("test");
        session.saveOrUpdate(keyword);
        
        AuditLogRecord record = new AuditLogRecord("new", keyword, "username");

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
}
