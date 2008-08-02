package org.tonguetied.audit;

import java.util.List;

import org.springframework.dao.DataAccessException;


/**
 * Interface defining Audit DAO facade for TongueTied storage.
 * 
 * @author mforslund
 * @author bsion
 */
public interface AuditRepository 
{
    /**
     * Persist an {@link AuditLogRecord} to permanent storage.
     * 
     * @param record the item to save or update.
     * @throws DataAccessException if the operation fails.
     */
    void saveOrUpdate(AuditLogRecord record) throws DataAccessException;
    
    /**
     * Remove an {@link AuditLogRecord} from permanent storage.
     * 
     * @param record the item to remove.
     */
    void delete(AuditLogRecord record);

    /**
     * Retrieve a list of all {@link AuditLogRecord}s from permanent storage. 
     * The return list will be ordered in reverse chronological order.
     * 
     * @return a list of all {@link AuditLogRecord} in the system
     */
    List<AuditLogRecord> getAuditLog();
}
