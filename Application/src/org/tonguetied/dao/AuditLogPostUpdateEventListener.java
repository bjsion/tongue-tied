package org.tonguetied.dao;

import org.hibernate.event.PostUpdateEvent;
import org.hibernate.event.PostUpdateEventListener;
import org.tonguetied.domain.Auditable;


/**
 * Event listener to add a new audit log record after an update to an 
 * {@link Auditable} class has been made. 
 * 
 * @author bsion
 *
 */
public class AuditLogPostUpdateEventListener implements PostUpdateEventListener
{

    private static final long serialVersionUID = -6569516811701521612L;

    /* (non-Javadoc)
     * @see org.hibernate.event.PostUpdateEventListener#onPostUpdate(org.hibernate.event.PostUpdateEvent)
     */
    public void onPostUpdate(PostUpdateEvent event) {
        if (event.getEntity() instanceof Auditable) {
            AuditLog.logEvent("update", (Auditable)event.getEntity(), event.getPersister().getFactory());
        }
    }

}
