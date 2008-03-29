package org.tonguetied.keywordmanagement;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

import org.apache.commons.lang.builder.CompareToBuilder;
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
@NamedQuery(name="get.bundles",query="from Bundle b order by b.name")
public class Bundle implements Comparable<Object>
{
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    @Column(unique=true,nullable=false)
    private String name;
    private String description;
    private String resourceName;
    private String resourceDestination;
    
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
    public String getResourceDestination() {
    	return resourceDestination;
    }
    public void setResourceDestination(String resourceDestination) {
    	this.resourceDestination = resourceDestination;
    }
    public String getResourceName() {
    	return resourceName;
    }
    public void setResourceName(String resourceName) {
    	this.resourceName = resourceName;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(Object object) {
        final Bundle other = (Bundle) object;
        return new CompareToBuilder().append(name, other.name).
                append(resourceName, other.resourceName).
                append(resourceDestination, other.resourceDestination).
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
            
            isEqual = (this.description == null? 
                            other.description == null: 
                            description.equals(other.description))
                    && (this.resourceName == null?
                            other.resourceName == null: 
                            resourceName.equals(other.resourceName))
                    && (this.resourceDestination == null? 
                            other.resourceDestination == null:
                            resourceDestination.equals(other.resourceDestination))
                    && (this.name == null? 
                            other.name == null: name.equals(other.name));
        }
        
        return isEqual;
    }
    
    @Override
    public int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder(19, 11);
        builder.append(resourceName).
            append(resourceDestination).
            append(name).
            append(description);

        return builder.toHashCode();
    }
    
    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this, 
                ToStringStyle.SHORT_PREFIX_STYLE).toString();
    }
}
