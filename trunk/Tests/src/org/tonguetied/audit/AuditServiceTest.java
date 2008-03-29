package org.tonguetied.audit;

import java.util.List;

import org.junit.Test;
import org.tonguetied.audit.AuditLogRecord;
import org.tonguetied.audit.AuditService;
import org.tonguetied.keywordmanagement.Keyword;
import org.tonguetied.keywordmanagement.KeywordService;
import org.tonguetied.test.common.AbstractServiceTest;
import org.tonguetied.usermanagement.User;
import org.tonguetied.usermanagement.UserService;


/**
 * @author bsion
 *
 */
public class AuditServiceTest extends AbstractServiceTest {

    private Keyword keyword1;
    private User user1;
    
    private KeywordService keywordService;
    private UserService userService;
    private AuditService auditService;

    @Override
    protected void onSetUpInTransaction() throws Exception {
        keyword1 = new Keyword();
        keyword1.setKeyword("akeyword");
        keyword1.setContext("keyword 1");
        
        user1 = new User("username", "password", "firstName", "lastName", "test@test.com", true, true, true, true); 

        userService.saveOrUpdate(user1);
        keywordService.saveOrUpdate(keyword1);
    }
    
    /**
     * Test method for {@link org.tonguetied.audit.AuditService#getAuditLog()}.
     */
    @Test
    public void testGetAuditLog() {
        AuditLogRecord record1 = new AuditLogRecord("test 1", keyword1, user1);
        AuditLogRecord record2 = new AuditLogRecord("test 2", keyword1, user1);
        AuditLogRecord record3 = new AuditLogRecord("test 3", keyword1, user1);
        keywordService.saveOrUpdate(record1);
        keywordService.saveOrUpdate(record2);
        keywordService.saveOrUpdate(record3);
        
        List<AuditLogRecord> auditLog = auditService.getAuditLog();
        assertEquals(3, auditLog.size());
        assertEquals(record3, auditLog.get(0));
        assertEquals(record2, auditLog.get(1));
        assertEquals(record1, auditLog.get(2));
    }

    public void setKeywordService(KeywordService keywordService) {
        this.keywordService = keywordService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setAuditService(AuditService auditService) {
        this.auditService = auditService;
    }
}
