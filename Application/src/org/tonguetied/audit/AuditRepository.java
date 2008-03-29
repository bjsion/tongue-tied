package org.tonguetied.audit;

import java.util.List;

import org.springframework.dao.DataAccessException;


/**
 * Interface defining Audit DAO facade for TongueTied storage.
 * 
 * @author mforslund
 */
public interface AuditRepository 
{
    /**
     * Persist an object to permanent storage.
     * 
     * @param object the item to save or update.
     * @throws DataAccessException if the operation fails.
     */
    void saveOrUpdate(Object object) throws DataAccessException;
    
    /**
     * Remove an object from permanent storage.
     * 
     * @param object the item to remove.
     */
    void delete(Object object);

    /**
     * Retrieve a list of all {@link AuditLogRecord}s from permanent storage. 
     * The return list will be ordered in reverse chronological order.
     * 
     * @return a list of all {@link AuditLogRecord} in the system
     */
    List<AuditLogRecord> getAuditLog();
}
