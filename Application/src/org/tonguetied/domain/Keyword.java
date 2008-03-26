package org.tonguetied.domain;

import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.SetUtils;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.AccessType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;

/**
 * A <code>Keyword</code> object maps a relationship between a list of 
 * {@link Translation}s a key string.  
 * 
 * @author mforslund
 */
@Entity
@AccessType("property")
@NamedQuery(name="get.keywords",query="from Keyword k order by k.keyword")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Keyword implements Cloneable, Comparable<Object>, Auditable
{
    private Long id;
    private String keyword;
    private String context;
    private SortedSet<Translation> translations;
    
    // This attribute is used for optimistic concurrency control in DB    
    private Integer version;
    
    public Keyword() {
        this.translations = new TreeSet<Translation>();
    }
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Long getId() {
        return id;
    }
        
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * 
     * @return the key string to which {@link Translation}s are mapped
     */
    @Column(unique=true,nullable=false)
    public String getKeyword() {
        return keyword;
    }
    
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
    
    /**
     * 
     * @return the string describing the use of the {@linkplain Keyword}
     */
    public String getContext() {
        return context;
    }
    
    public void setContext(String context) {
        this.context = context;
    }
    
    /**
     * 
     * @return the list of {@link Translation}s mapped to the key string 
     */
    @OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    @Sort(type=SortType.NATURAL)
    @JoinColumn(name="KEYWORD_ID")
    @Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    public SortedSet<Translation> getTranslations() {
    	return translations;
    }
    
    public void setTranslations(SortedSet<Translation> translations) {
    	this.translations = translations;
    }
    
    /**
     * Add a {@link Translation} to this <code>Keyword</code>s list of 
     * <code>translations</code>. This method is a convenience method acting as
     * wrapper around the lists' add method. 
     *  
     * @param translation the {@link Translation} to add.
     */
    public void addTranslation(Translation translation) {
        this.translations.add(translation);
    }
    
    /**
     * Remove a {@link Translation} from this Keyword's list of 
     * <code>translations</code>. The translation to be removed is matched by
     * the <code>id</code> of the {@link Translation}.
     * 
     * @param translationId the id of the {@link Translation} to remove
     */
    public void removeTranslation(Long translationId) {
        Translation translation = (Translation) CollectionUtils.find(
                translations, new TranslationPredicate(translationId));
        remove(translation);
    }
    
    /**
     * Remove a {@link Translation} from this <code>Keyword</code>s list of 
     * <code>translations</code>. This method is a convenience method acting as
     * a wrapper around the lists' remove method. 
     *  
     * @param translation the {@link Translation} to remove.
     */
    public void remove(Translation translation) {
        this.translations.remove(translation);
    }
    
    /**
     * This field is used for optimistic locking.
     * 
     * @return the version
     */
    @Version
    @Column(name="OPTLOCK")
    public Integer getVersion() {
        return version;
    }

    /**
     * This field is used for optimistic locking.
     * 
     * @param version the version to set
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(Object object) {
        final Keyword other = (Keyword) object;
        return new CompareToBuilder().append(keyword, other.keyword).
                append(context, other.context).
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
        else if (obj instanceof Keyword)
        {
            final Keyword other = (Keyword)obj;
            
            isEqual = (this.keyword == null? 
                        other.keyword == null: keyword.equals(other.keyword))
                    && (this.context == null?
                        other.context == null: context.equals(other.context))
                    && (this.translations == null?
                        other.translations == null:
                            SetUtils.isEqualSet(translations, other.translations));
//                            translations.equals(other.translations));
        }
        
        return isEqual;
    }
    
    @Override
    public int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder(23, 17);
        int hashCode = builder.append(keyword).append(context).toHashCode();
//        hashCode =+ SetUtils.hashCodeForSet(translations);
        
        return hashCode;
    }
    
    /**
     * Performs a deep copy of this keyword object.
     *   
     * @see java.lang.Object#clone()
     */
    @Override
    public final Keyword clone() {
        Keyword clone;
        try {
            clone = (Keyword) super.clone();
            if (translations != null) {
                clone.setTranslations(new TreeSet<Translation>());
                for (Translation translation: translations) {
                    clone.addTranslation(translation.clone());
                }
            }
        }
        catch (CloneNotSupportedException cnse) {
            clone = null;
        }
        return clone;
    }

    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this, 
                ToStringStyle.SHORT_PREFIX_STYLE).toString();
    }
    
    /**
     * This predicate is used to find {@link Translation}s based off its 
     * primary key.
     * 
     * @author bsion
     *
     */
    protected static class TranslationPredicate implements Predicate {
        private Long id;
        
        /**
         * Create a new instance of TranslationPredicate.
         * 
         * @param id the <code>id</code> of the translation to find
         */
        public TranslationPredicate(Long id) {
            this.id = id;
        }
        
        /** 
         * Evaluate if a {@link Translation}s business keys are equal. This  
         * method evaluates if the {@link Language}, {@link Bundle} and
         * {@link Country} are equal
         * 
         * @return <code>true</code> if the {@link Translation} business keys
         * match. <code>false</code> otherwise
         * @see org.apache.commons.collections.Predicate#evaluate(java.lang.Object)
         */
        public boolean evaluate(Object object) {
            Translation translation = (Translation) object;
            
            return id.equals(translation.getId());
        }
    }
}
