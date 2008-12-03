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

import org.apache.log4j.Logger;
import org.hibernate.CallbackException;
import org.hibernate.Session;
import org.hibernate.engine.SessionFactoryImplementor;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.userdetails.UserDetails;


/**
 * Utility class used to persist audit log records.
 * 
 * @author bsion
 *
 */
public class AuditLog
{
    private static final Logger logger = Logger.getLogger(AuditLog.class);
    
    /**
     * Create an audit log entry.
     * 
     * @param message the message describing the action taken
     * @param entity
     * @param implementor
     * @throws CallbackException
     */
    public static synchronized void logEvent(final String message, Auditable entity, 
            SessionFactoryImplementor implementor)
    throws CallbackException 
    {
        Session tempSession = implementor.openTemporarySession();
        try
        {
            AuditLogRecord record = new AuditLogRecord(message, entity, getUsername());
            
            tempSession.save(record);
            tempSession.flush();
            if (logger.isDebugEnabled())
                logger.debug("successfully saved audit log record: " + record);
        }
        catch (Exception ex)
        {
            throw new CallbackException(ex);
        }
        finally
        {
            if (tempSession != null)
                tempSession.close();
        }
    }
    
    /**
     * Gets the current user name from the Spring Security SecurityContext.
     * 
     * @return current user, or <tt>null</tt> if no user is currently logged in
     */
    private static synchronized String getUsername()
    {
        SecurityContext secureContext = SecurityContextHolder.getContext();

        String username = null;
        // secure context will be null when running unit tests so leave userId
        // as null
        if (secureContext != null) {
            Authentication auth = secureContext.getAuthentication();

            if (auth.getPrincipal() instanceof UserDetails) {
                username = ((UserDetails) auth.getPrincipal()).getUsername();
            }
        }
        
        return username;
    }
}
