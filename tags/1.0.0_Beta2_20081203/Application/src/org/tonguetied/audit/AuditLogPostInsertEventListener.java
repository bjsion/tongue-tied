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
import org.hibernate.event.PostInsertEvent;
import org.hibernate.event.PostInsertEventListener;


/**
 * Event listener to add a new audit log record after an insert to an 
 * {@link Auditable} class has been made.
 *  
 * @author bsion
 *
 */
public class AuditLogPostInsertEventListener implements PostInsertEventListener
{
    private static final Logger logger = 
        Logger.getLogger(AuditLogPostDeleteEventListener.class);

    private static final long serialVersionUID = -8154917326845979720L;

    /* (non-Javadoc)
     * @see org.hibernate.event.PostInsertEventListener#onPostInsert(org.hibernate.event.PostInsertEvent)
     */
    public void onPostInsert(PostInsertEvent event) {
        if (event.getEntity() instanceof Auditable) {
            Auditable entity = (Auditable)event.getEntity();
            if (logger.isDebugEnabled())
                logger.debug("Adding an audit log entry for insertion of entity "
                        + entity.getClass() + " with id " +entity.getId());
            
            AuditLog.logEvent("new", entity, event.getPersister().getFactory());
        }
    }
}
