package org.tonguetied.audit;

import org.hibernate.event.PostDeleteEvent;
import org.hibernate.event.PostDeleteEventListener;


/**
 * Event listener to add a new audit log record after a delete to an 
 * {@link Auditable} class has been made.
 *  
 * @author bsion
 *
 */
public class AuditLogPostDeleteEventListener implements PostDeleteEventListener
{

    private static final long serialVersionUID = 6638781488543805970L;

    /* (non-Javadoc)
     * @see org.hibernate.event.PostDeleteEventListener#onPostDelete(org.hibernate.event.PostDeleteEvent)
     */
    public void onPostDelete(PostDeleteEvent event) {
        if (event.getEntity() instanceof Auditable) {
            AuditLog.logEvent("delete", (Auditable)event.getEntity(), event.getPersister().getFactory());
        }
    }
}
