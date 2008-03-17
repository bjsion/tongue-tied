package org.tonguetied.service;

import java.util.List;

import org.tonguetied.domain.AuditLogRecord;


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
