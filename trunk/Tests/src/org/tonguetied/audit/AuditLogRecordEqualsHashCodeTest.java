package org.tonguetied.audit;

import org.tonguetied.audit.AuditLogRecord;
import org.tonguetied.keywordmanagement.Keyword;
import org.tonguetied.usermanagement.User;

import junitx.extensions.EqualsHashCodeTestCase;


/**
 * @author bsion
 *
 */
public class AuditLogRecordEqualsHashCodeTest extends EqualsHashCodeTestCase {

    public AuditLogRecordEqualsHashCodeTest(String name) {
        super(name);
    }

    @Override
    protected Object createInstance() throws Exception {
        User user = new User("username", "password", "firstName", "lastName", "test@test.com", true, true, true, true);
        
        Keyword keyword = new Keyword();
        keyword.setKeyword("test");
        
        AuditLogRecord record = new AuditLogRecord("new", keyword, user);

        return record;
    }

    @Override
    protected Object createNotEqualInstance() throws Exception {
        User user = new User("username", "password", "firstName", "lastName", "test@test.com", true, true, true, true);
        
        Keyword keyword = new Keyword();
        keyword.setKeyword("test");
        
        AuditLogRecord record = new AuditLogRecord("delete", keyword, user);

        return record;
    }
}
