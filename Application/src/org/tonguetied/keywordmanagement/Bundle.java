package org.tonguetied.keywordmanagement;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.AccessType;

/**
 * 
 * @author mforslund
 */
@Entity
@AccessType("field")
@NamedQueries({
    @NamedQuery(name="get.bundles",query="from Bundle b order by b.name"),
    @NamedQuery(name="get.default.bundle",query="from Bundle b where b.isDefault = true")
})
public class Bundle implements Comparable<Object>
{
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    @Column(unique=true,nullable=false)
    private String name;
    private String description;
    private String resourceName;
    
    /**
     * Only one bundle will have a value of true
     */
    private boolean isDefault;
    
    public String getDescription() {
    	return description;
    }
    public void setDescription(String description) {
    	this.description = description;
    }
    public Long getId() {
    	return id;
    }
    public void setId(Long id) {
    	this.id = id;
    }
    public String getName() {
    	return name;
    }
    public void setName(String name) {
    	this.name = name;
    }
    public String getResourceName() {
    	return resourceName;
    }
    public void setResourceName(String resourceName) {
    	this.resourceName = resourceName;
    }
    public boolean isDefault() {
        return isDefault;
    }
    public void setDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(Object object) {
        final Bundle other = (Bundle) object;
        return new CompareToBuilder().append(name, other.name).
                append(resourceName, other.resourceName).
                append(isDefault, other.isDefault).
                toComparison();
    }
    
    @Override
    public boolean equals(Object obj) {
        boolean isEqual = false;
        // a good optimization
        if (this == obj)
        {
            isEqual = true;
        }
        else if (obj instanceof Bundle)
        {
            final Bundle other = (Bundle)obj;
            EqualsBuilder builder = new EqualsBuilder();
            builder.append(this.description, other.description).
                append(this.name, other.name).
                append(this.resourceName, other.resourceName).
                append(this.isDefault, other.isDefault);
            isEqual = builder.isEquals();
        }
        
        return isEqual;
    }
    
    @Override
    public int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder(19, 11);
        builder.append(resourceName).
            append(name).
            append(description).
            append(isDefault);

        return builder.toHashCode();
    }
    
    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this, 
                ToStringStyle.SHORT_PREFIX_STYLE).toString();
    }
}
