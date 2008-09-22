/*
 * Copyright 2008 The Tongue-Tied Authors
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not 
 * use this file except in compliance with the License. You may obtain a copy 
 * of the License at
 *  
 * http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the 
 * License for the specific language governing permissions and limitations 
 * under the License. 
 */
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
