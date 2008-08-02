package org.tonguetied.audit;

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
public class AuditLog {
    
    /**
     * Create an audit log entry.
     * 
     * @param message the message describing the action taken
     * @param entity
     * @param implementor
     * @throws CallbackException
     */
    public static synchronized void logEvent(String message, Auditable entity, 
            SessionFactoryImplementor implementor)
    throws CallbackException 
    {
        Session tempSession = implementor.openTemporarySession();
        try {
            AuditLogRecord record = new AuditLogRecord(message, entity, getUsername());
            
            tempSession.save(record);
            tempSession.flush();
        }
        catch (Exception ex) {
            throw new CallbackException(ex);
        }
        finally {
            if (tempSession != null)
                tempSession.close();
        }
    }
    
    /**
     * Gets the current user name from the Spring Security SecurityContext.
     * 
     * @return current user, or <tt>null</tt> if no user is currently logged in
     */
    private static synchronized String getUsername() {
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
