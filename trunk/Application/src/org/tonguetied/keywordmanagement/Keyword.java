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
package org.tonguetied.keywordmanagement;

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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
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
import org.tonguetied.audit.Auditable;

/**
 * A <code>Keyword</code> object maps a relationship between a list of 
 * {@link Translation}s a key string.
 * 
 * @author mforslund
 * @author bsion
 */
@Entity
@AccessType("property")
@NamedQuery(name=Keyword.QUERY_GET_KEYWORDS,query="from Keyword k order by k.keyword")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name=Keyword.TABLE_KEYWORD)
public class Keyword implements Cloneable, Comparable<Keyword>, Auditable
{
    private Long id;
    private String keyword;
    private String context;
    private SortedSet<Translation> translations;
    
    public static final String TABLE_KEYWORD = "keyword";
    private static final String COL_ID = TABLE_KEYWORD + "_id";
    public static final String QUERY_GET_KEYWORDS = "get.keywords";
    
    // This attribute is used for optimistic concurrency control in DB
    private Integer version;
    
    public Keyword()
    {
        this.translations = new TreeSet<Translation>();
    }
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO,generator="keyword_generator")
    @SequenceGenerator(name="keyword_generator",sequenceName="keyword_id_seq")
    @Column(name=COL_ID)
    public Long getId()
    {
        return id;
    }
        
    public void setId(final Long id) {
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
    
    public void setKeyword(final String keyword) {
        this.keyword = keyword;
    }
    
    /**
     * 
     * @return the string describing the use of the {@linkplain Keyword}
     */
    public String getContext() {
        return context;
    }
    
    public void setContext(final String context) {
        this.context = context;
    }
    
    /**
     * 
     * @return the list of {@link Translation}s mapped to the key string 
     */
    @OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    @Sort(type=SortType.NATURAL)
    @JoinColumn(name="KEYWORD_ID")
    @Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
    public SortedSet<Translation> getTranslations()
    {
        return translations;
    }
    
    public void setTranslations(SortedSet<Translation> translations)
    {
        this.translations = translations;
    }
    
    /**
     * Add a {@link Translation} to this <code>Keyword</code>s list of 
     * <code>translations</code>. This method is a convenience method acting as
     * wrapper around the lists' add method. 
     *  
     * @param translation the {@link Translation} to add.
     */
    public void addTranslation(Translation translation)
    {
        this.translations.add(translation);
    }
    
    /**
     * Remove a {@link Translation} from this Keyword's list of 
     * <code>translations</code>. The translation to be removed is matched by
     * the <code>id</code> of the {@link Translation}.
     * 
     * @param translationId the id of the {@link Translation} to remove
     */
    public void removeTranslation(final Long translationId)
    {
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
    public void remove(Translation translation)
    {
        this.translations.remove(translation);
    }
    
    /**
     * This field is used for optimistic locking.
     * 
     * @return the version
     */
    @Version
    @Column(name="optlock")
    public Integer getVersion()
    {
        return version;
    }

    /**
     * This field is used for optimistic locking.
     * 
     * @param version the version to set
     */
    public void setVersion(Integer version)
    {
        this.version = version;
    }

    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(final Keyword other)
    {
        return new CompareToBuilder().append(keyword, other.keyword).
                append(context, other.context).
                toComparison();
    }
    
    @Override
    public boolean equals(Object obj)
    {
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
    public int hashCode()
    {
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
    public final Keyword clone()
    {
        Keyword clone;
        try
        {
            clone = (Keyword) super.clone();
            if (translations != null) {
                clone.setTranslations(new TreeSet<Translation>());
                for (Translation translation: translations)
                {
                    clone.addTranslation(translation.clone());
                }
            }
        }
        catch (CloneNotSupportedException cnse)
        {
            clone = null;
        }
        return clone;
    }

    @Override
    public String toString()
    {
        return new ReflectionToStringBuilder(this, 
                ToStringStyle.SHORT_PREFIX_STYLE).toString();
    }
    
    @Override
    public String toLogString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Keyword[keyword=").append(keyword).append(",context=").
        append(context).append(",translations=[");
        for (Translation translation : translations)
        {
            builder.append("bundle=");
            builder.append(translation.getBundle().getName());
            builder.append(", country=");
            builder.append(translation.getCountry().getCode());
            builder.append(", language=");
            builder.append(translation.getLanguage().getCode());
            builder.append(", state=");
            builder.append(translation.getState());
            builder.append(", value=");
            builder.append(translation.getValue());
        }
        builder.append("]]");
        
        return builder.toString();
    }

    /**
     * This predicate is used to find {@link Translation}s based off its 
     * primary key.
     * 
     * @author bsion
     *
     */
    protected static class TranslationPredicate implements Predicate
    {
        private Long id;
        
        /**
         * Create a new instance of TranslationPredicate.
         * 
         * @param id the <code>id</code> of the translation to find
         */
        public TranslationPredicate(final Long id)
        {
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
        public boolean evaluate(Object object)
        {
            Translation translation = (Translation) object;
            
            return id.equals(translation.getId());
        }
    }
}
