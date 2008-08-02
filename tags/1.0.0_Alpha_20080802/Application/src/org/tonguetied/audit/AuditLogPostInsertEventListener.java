package org.tonguetied.audit;

import org.hibernate.event.PostInsertEvent;
import org.hibernate.event.PostInsertEventListener;


/**
 * Event listener to add a new audit log record after an insert to an 
 * {@link Auditable} class has been made.
 *  
 * @author bsion
 *
 */
public class AuditLogPostInsertEventListener implements PostInsertEventListener {

    private static final long serialVersionUID = -8154917326845979720L;

    /* (non-Javadoc)
     * @see org.hibernate.event.PostInsertEventListener#onPostInsert(org.hibernate.event.PostInsertEvent)
     */
    public void onPostInsert(PostInsertEvent event) {
        if (event.getEntity() instanceof Auditable) {
            AuditLog.logEvent("new", (Auditable)event.getEntity(), event.getPersister().getFactory());
        }
    }
}
