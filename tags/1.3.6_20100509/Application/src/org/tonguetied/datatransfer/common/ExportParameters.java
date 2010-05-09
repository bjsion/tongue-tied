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
package org.tonguetied.datatransfer.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.tonguetied.keywordmanagement.Bundle;
import org.tonguetied.keywordmanagement.Country;
import org.tonguetied.keywordmanagement.Language;
import org.tonguetied.keywordmanagement.Translation.TranslationState;

/**
 * Value object to transport export selection criteria. This object does not
 * have a state.
 * 
 * @author bsion
 * 
 */
public class ExportParameters
{
    private List<Country> countries;
    private List<Bundle> bundles;
    private List<Language> languages;
    private TranslationState translationState;
    private FormatType formatType;
    private boolean isResultPackaged;
    private boolean isGlobalsMerged;

    /**
     * Create a new instance of the export parameters.
     */
    public ExportParameters()
    {
        this.countries = new ArrayList<Country>();
        this.bundles = new ArrayList<Bundle>();
        this.languages = new ArrayList<Language>();
    }

    /**
     * @return the collection of {@link Bundle}s to export
     */
    public List<Bundle> getBundles()
    {
        return bundles;
    }

    /**
     * @param bundles the collection of bundles to export
     */
    public void setBundles(List<Bundle> bundles)
    {
        this.bundles = bundles;
    }

    public void addBundle(final Bundle bundle)
    {
        this.bundles.add(bundle);
    }

    /**
     * @return the collection of {@link Country}s selected to export
     */
    public List<Country> getCountries()
    {
        return countries;
    }

    /**
     * @param countries the collection of {@link Country}s to export
     */
    public void setCountries(List<Country> countries)
    {
        this.countries = countries;
    }

    public void addCountry(final Country country)
    {
        this.countries.add(country);
    }

    /**
     * @return the {@link FormatType} to use for this export
     */
    public FormatType getFormatType()
    {
        return formatType;
    }

    /**
     * @param formatType the {@link FormatType} to set
     */
    public void setFormatType(final FormatType formatType)
    {
        this.formatType = formatType;
    }

    /**
     * @return the collection of {@link Language}s to export
     */
    public List<Language> getLanguages()
    {
        return languages;
    }

    /**
     * @param languages the collection of {@link Language}s to export
     */
    public void setLanguages(List<Language> languages)
    {
        this.languages = languages;
    }

    public void addLanguage(final Language language)
    {
        this.languages.add(language);
    }

    /**
     * @return the {@link TranslationState} to export
     */
    public TranslationState getTranslationState()
    {
        return translationState;
    }

    /**
     * @param translationState the {@link TranslationState} to export
     */
    public void setTranslationState(final TranslationState translationState)
    {
        this.translationState = translationState;
    }

    /**
     * @return a flag indicating if the resultant export files should be
     *         packaged and compressed into one file
     */
    public boolean isResultPackaged()
    {
        return isResultPackaged;
    }

    /**
     * @param isResultPackaged the isResultPackaged to set
     */
    public void setResultPackaged(final boolean isResultPackaged)
    {
        this.isResultPackaged = isResultPackaged;
    }

    /**
     * @return a flag indicating if global bundle translations should be merged
     * into the normal bundles
     */
    public boolean isGlobalsMerged()
    {
        return isGlobalsMerged;
    }

    /**
     * @param isGlobalsMerged the isGlobalsMerged to set
     */
    public void setGlobalsMerged(final boolean isGlobalsMerged)
    {
        this.isGlobalsMerged = isGlobalsMerged;
    }

    @Override
    public String toString()
    {
        return new ReflectionToStringBuilder(this,
                ToStringStyle.SHORT_PREFIX_STYLE).toString();
    }
}
