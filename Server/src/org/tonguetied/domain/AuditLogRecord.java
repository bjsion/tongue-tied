package org.tonguetied.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author bsion
 *
 */
@Entity
@org.hibernate.annotations.Entity(mutable=false)
@NamedQuery(name="get.audit.log",query="from AuditLogRecord alr order by alr.created desc")
public class AuditLogRecord {
    private Long id;
    private String message;
    private Long entityId;
    private Class<?> entityClass;
    private User user;
    private Date created;
    
    AuditLogRecord() {
    }

    /**
     * @param message
     * @param entity
     * @param user
     */
    public AuditLogRecord(String message, Auditable entity, User user) {
        this.message = message;
        this.entityId = entity.getId();
        this.entityClass = entity.getClass();
        this.user = user;
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
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the entityId
     */
    @Column(nullable=false)
    public Long getEntityId() {
        return entityId;
    }

    /**
     * @param entityId the entityId to set
     */
    public void setEntityId(Long entityId) {
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
     * @return the user who made the change, or <tt>null</tt> if it was done
     * anonymously
     */
    @OneToOne
//    @PrimaryKeyJoinColumn
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return the created
     */
    @Column(nullable=false)
    public Date getCreated() {
        return created;
    }

    /**
     * @param created the created to set
     */
    public void setCreated(Date created) {
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
                    append(user, other.user).
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
                    append(user).
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
