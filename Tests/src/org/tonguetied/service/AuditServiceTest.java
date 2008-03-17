/**
 * 
 */
package org.tonguetied.service;

import java.util.List;

import org.junit.Test;
import org.tonguetied.domain.AuditLogRecord;
import org.tonguetied.domain.Keyword;
import org.tonguetied.domain.User;


/**
 * @author bsion
 *
 */
public class AuditServiceTest extends AbstractServiceTest {

    private Keyword keyword1;
    private User user1;
    
    private ApplicationService appService;
    private UserService userService;
    private AuditService auditService;

    @Override
    protected void onSetUpInTransaction() throws Exception {
        keyword1 = new Keyword();
        keyword1.setKeyword("akeyword");
        keyword1.setContext("keyword 1");
        
        user1 = new User("username", "password", "firstName", "lastName", "test@test.com", true, true, true, true); 

        userService.saveOrUpdate(user1);
        appService.saveOrUpdate(keyword1);
    }
    
    /**
     * Test method for {@link org.tonguetied.service.AuditService#getAuditLog()}.
     */
    @Test
    public void testGetAuditLog() {
        AuditLogRecord record1 = new AuditLogRecord("test 1", keyword1, user1);
        AuditLogRecord record2 = new AuditLogRecord("test 2", keyword1, user1);
        AuditLogRecord record3 = new AuditLogRecord("test 3", keyword1, user1);
        appService.saveOrUpdate(record1);
        appService.saveOrUpdate(record2);
        appService.saveOrUpdate(record3);
        
        List<AuditLogRecord> auditLog = auditService.getAuditLog();
        assertEquals(3, auditLog.size());
        assertEquals(record3, auditLog.get(0));
        assertEquals(record2, auditLog.get(1));
        assertEquals(record1, auditLog.get(2));
    }

    public void setAppService(ApplicationService appService) {
        this.appService = appService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setAuditService(AuditService auditService) {
        this.auditService = auditService;
    }
}
