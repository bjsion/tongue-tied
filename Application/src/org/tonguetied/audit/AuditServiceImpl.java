package org.tonguetied.audit;

import java.util.List;

/**
 * @author bsion
 *
 */
public class AuditServiceImpl implements AuditService {
    private AuditRepository auditRepository;

    public List<AuditLogRecord> getAuditLog() {
        return auditRepository.getAuditLog();
    }

    /**
     * @param auditRepository the auditRepository to set
     */
    public void setAuditRepository(AuditRepository auditRepository) {
        this.auditRepository = auditRepository;
    }
}
