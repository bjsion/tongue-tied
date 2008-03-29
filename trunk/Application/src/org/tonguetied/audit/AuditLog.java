package org.tonguetied.audit;

import org.acegisecurity.Authentication;
import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.userdetails.UserDetails;
import org.hibernate.CallbackException;
import org.hibernate.Session;
import org.hibernate.engine.SessionFactoryImplementor;
import org.tonguetied.usermanagement.User;


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
            AuditLogRecord record = new AuditLogRecord(message, entity, getUser());
            
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
     * Gets the current user from the Acegi secureContext
     * 
     * @return current user, or <tt>null</tt> if no user is currently logged in
     */
    private static synchronized User getUser() {
        SecurityContext secureContext = SecurityContextHolder.getContext();

        User user = null;
        // secure context will be null when running unit tests so leave userId
        // as null
        if (secureContext != null) {
            Authentication auth = secureContext.getAuthentication();

            if (auth.getPrincipal() instanceof UserDetails) {
                user = (User) auth.getPrincipal();
            }
        }
        return user;
    }
}
