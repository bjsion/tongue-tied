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

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Stateful object that contains a summary of a database transaction.
 * 
 * @author bsion
 *
 */
@Entity
@org.hibernate.annotations.Entity(mutable=false)
@NamedQuery(name=AuditLogRecord.QUERY_GET_AUDIT_LOG,
        query="from AuditLogRecord alr order by alr.created desc")
public class AuditLogRecord {
    private Long id;
    private String message;
    private Long entityId;
    private Class<?> entityClass;
    private String username;
    private Date created;
    
    public static final String QUERY_GET_AUDIT_LOG = "get.audit.log";
    
    AuditLogRecord() {
    }

    /**
     * Create a new instance of the AuditLogRecord
     * 
     * @param message
     * @param entity
     * @param username the username of the user who made the change
     */
    public AuditLogRecord(final String message, final Auditable entity, final String username) {
        this.message = message;
        this.entityId = entity.getId();
        this.entityClass = entity.getClass();
        this.username = username;
        this.created = new Date();
    }

    /**
     * @return the the unique identifier for this record
     */
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the message describing the action taken
     */
    @Column(nullable=false)
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(final String message) {
        this.message = message;
    }

    /**
     * Gets the unique identifier of the entity that was changed.
     * 
     * @return the id of the entity
     */
    @Column(nullable=false)
    public Long getEntityId() {
        return entityId;
    }

    /**
     * @param entityId the entityId to set
     */
    public void setEntityId(final Long entityId) {
        this.entityId = entityId;
    }

    /**
     * @return the entityClass
     */
    @Column(nullable=false)
    public Class<?> getEntityClass() {
        return entityClass;
    }

    /**
     * @param entityClass the entityClass to set
     */
    public void setEntityClass(Class<?> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     * Gets the username of the <code>User</code> who made this change.
     * 
     * @return the username of the user who made the change, or <tt>null</tt> 
     * if it was done anonymously
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(final String username) {
        this.username = username;
    }

    /**
     * Returns a cloned instance of the date the record was created. This is 
     * done for security reasons, so that the internals of the date cannot be
     * changed.
     * 
     * @return the date the record was created
     */
    @Column(nullable=false)
    public Date getCreated() {
        return (Date) created.clone();
    }

    /**
     * @param created the date the record was created
     */
    public void setCreated(final Date created) {
        this.created = created;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        boolean isEqual = false;
        // a good optimization
        if (this == obj)
            isEqual = true;
        else if (obj != null && getClass() == obj.getClass()) 
        {
            final AuditLogRecord other = (AuditLogRecord) obj;
            EqualsBuilder builder = new EqualsBuilder();
            isEqual = builder.append(message, other.message).
                    append(entityId, other.entityId).
                    append(entityClass, other.entityClass).
                    append(username, other.username).
                    append(created, other.created).
                    isEquals();  
        }
            
        return isEqual;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder(17, 19);
        int hashCode = builder.append(message).
                    append(entityId).
                    append(entityClass).
                    append(username).
                    append(created).
                    toHashCode();
        
        return hashCode;
    }

    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this, 
                ToStringStyle.SHORT_PREFIX_STYLE).toString();
    }
}
