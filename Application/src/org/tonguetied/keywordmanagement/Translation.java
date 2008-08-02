package org.tonguetied.keywordmanagement;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.AccessType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A translation is a specific value of a {@link Keyword} for a 
 * {@link Language}, {@link Country} and {@link Bundle}.
 * 
 * @author mforslund
 * @author bsion
 */
@Entity
@AccessType("property")
@NamedQueries({
    @NamedQuery(name=Translation.QUERY_FIND_TRANSLATIONS,
                query="select translation " +
                      "from Translation translation " +
                      "where translation.country in (:countries) " +
                      "and translation.bundle in (:bundles) " +
                      "and translation.language in (:languages) " +
                      "and translation.state = :state " +
                      "order by lower(translation.keyword.keyword), " +
                          "translation.language, " +
                          "translation.country, " +
                          "translation.bundle")
})
@Table(name="Translation",uniqueConstraints={@UniqueConstraint(columnNames={"keyword_id","language_id","country_id","bundle_id"})})
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Translation implements Cloneable, Comparable<Translation>
{
    private Long id;
    private String value;
    private Language language;
    private Country country;
    private Bundle bundle;
    private Keyword keyword;
    private TranslationState state;
    
    // This attribute is used for optimistic concurrency control in DB    
    private Integer version;
    
    /**
     * Name of the query to search for translations.
     */
    public static final String QUERY_FIND_TRANSLATIONS = "find.translations";
    
    /**
     * Create a new instance of Translation. This constructor initialises all
     * the required fields.
     */
    public Translation() {
        this.state = TranslationState.UNVERIFIED;
    }
    
    /**
     * Create a new instance of Translation.
     * 
     * @param bundle the {@link Bundle} of this Translation
     * @param country the {@link Country} of this Translation
     * @param language the {@link Language} of this Translation
     * @param value the translated value
     * @param state the state of this Translation
     * @throws IllegalArgumentException if the <code>state</code> parameter is
     * <code>null</code>
     */
    public Translation(Bundle bundle, Country country, Language language,
            String value, TranslationState state) 
    {
        if (state == null) 
            throw new IllegalArgumentException("state cannot be null");
        
        this.value = value;
        this.language = language;
        this.country = country;
        this.bundle = bundle;
        this.state = state;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Long getId() {
        return id;
    }
    public void setId(final Long id) {
        this.id = id;
    }
    
    @OneToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name="BUNDLE_ID")
    public Bundle getBundle() {
    	return bundle;
    }
    public void setBundle(final Bundle bundle) {
    	this.bundle = bundle;
    }
    
    @OneToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name="COUNTRY_ID")
    public Country getCountry() {
    	return country;
    }
    public void setCountry(final Country country) {
    	this.country = country;
    }

    @ManyToOne
    @JoinColumn(name="KEYWORD_ID",insertable=false,updatable=false)
    public Keyword getKeyword() {
    	return keyword;
    }
    public void setKeyword(final Keyword keyword) {
    	this.keyword = keyword;
    }
    
    @OneToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name="LANGUAGE_ID")
    public Language getLanguage() {
    	return language;
    }
    public void setLanguage(final Language language) {
    	this.language = language;
    }
    public String getValue() {
    	return value;
    }
    public void setValue(final String value) {
    	this.value = value;
    }
    
    /**
     * @return the flag indicating if the workflow state of this Translation
     */
    @Column(nullable=false,length=10)
    @Enumerated(EnumType.STRING)
    public TranslationState getState() {
        return state;
    }
    
    /**
     * @param state the {@link TranslationState} to set
     */
    public void setState(final TranslationState state) {
        this.state = state;
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
    public int compareTo(final Translation other)
    {
        return new CompareToBuilder().append(keyword, other.keyword).
                append(language, other.language).
                append(country, other.country).
                append(bundle, other.bundle).
                append(value, other.value).
                append(state, other.state).
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
        else if (obj instanceof Translation)
        {
            final Translation other = (Translation)obj;
            
            EqualsBuilder builder = new EqualsBuilder();
            isEqual = builder.append(value, other.value).
                append(bundle, other.bundle).
                append(country, other.country).
                append(language, other.language).
                append(keyword, other.keyword).
                append(state, other.state).
                isEquals();
        }
        
        return isEqual;
    }
    
    @Override
    public int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder(33, 19);
        builder.append(keyword).append(bundle).append(country).append(language).
            append(value).append(state);
//            append(keyword).append(value);

        return builder.toHashCode();
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#clone()
     */
    @Override
    public Translation clone() {
        Translation clone;
        try {
            clone = (Translation) super.clone(); 
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
     * These values represent the workflow states for a translation.
     * 
     * @author bsion
     *
     */
    public static enum TranslationState {
        UNVERIFIED, VERIFIED, QUERIED
    }
}
