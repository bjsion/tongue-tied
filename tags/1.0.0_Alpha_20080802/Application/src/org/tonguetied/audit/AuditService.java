package org.tonguetied.audit;

import java.util.List;



/**
 * @author bsion
 *
 */
public interface AuditService {

    /**
     * Retrieve a list of all {@link AuditLogRecord}s in the system. 
     * 
     * @return a list of all {@link AuditLogRecord} in the system
     */
    List<AuditLogRecord> getAuditLog();
}
