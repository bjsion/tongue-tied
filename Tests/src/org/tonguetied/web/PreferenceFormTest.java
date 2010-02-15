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
package org.tonguetied.web;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.tonguetied.keywordmanagement.Bundle;
import org.tonguetied.keywordmanagement.Country;
import org.tonguetied.keywordmanagement.Language;

/**
 * Test class for the {@link PreferenceForm} class.
 * 
 * @author bsion
 *
 */
public class PreferenceFormTest
{
    /**
     * Test method for {@link org.tonguetied.web.PreferenceForm#isTranslationFilterApplied()}.
     */
    @Test
    public final void testIsTranslationFilterAppliedForAllEmpty()
    {
        PreferenceForm preferenceForm = new PreferenceForm();
        assertFalse(preferenceForm.isTranslationFilterApplied());
    }

    /**
     * Test method for {@link org.tonguetied.web.PreferenceForm#isTranslationFilterApplied()}.
     */
    @Test
    public final void testIsTranslationFilterAppliedWithBundle()
    {
        PreferenceForm preferenceForm = new PreferenceForm();
        final Bundle bundle = new Bundle();
        preferenceForm.addBundle(bundle);
        assertTrue(preferenceForm.isTranslationFilterApplied());
    }
    
    /**
     * Test method for {@link org.tonguetied.web.PreferenceForm#isTranslationFilterApplied()}.
     */
    @Test
    public final void testIsTranslationFilterAppliedWithNullBundles()
    {
        PreferenceForm preferenceForm = new PreferenceForm();
        preferenceForm.setSelectedBundles(null);
        assertFalse(preferenceForm.isTranslationFilterApplied());
    }
    
    /**
     * Test method for {@link org.tonguetied.web.PreferenceForm#isTranslationFilterApplied()}.
     */
    @Test
    public final void testIsTranslationFilterAppliedWithNullCountry()
    {
        PreferenceForm preferenceForm = new PreferenceForm();
        preferenceForm.setSelectedCountries(null);
        assertFalse(preferenceForm.isTranslationFilterApplied());
    }
    
    /**
     * Test method for {@link org.tonguetied.web.PreferenceForm#isTranslationFilterApplied()}.
     */
    @Test
    public final void testIsTranslationFilterAppliedWithCountry()
    {
        PreferenceForm preferenceForm = new PreferenceForm();
        final Country country = new Country();
        preferenceForm.addCountry(country);
        assertTrue(preferenceForm.isTranslationFilterApplied());
    }
    
    /**
     * Test method for {@link org.tonguetied.web.PreferenceForm#isTranslationFilterApplied()}.
     */
    @Test
    public final void testIsTranslationFilterAppliedWithNullLanguage()
    {
        PreferenceForm preferenceForm = new PreferenceForm();
        preferenceForm.setSelectedLanguages(null);
        assertFalse(preferenceForm.isTranslationFilterApplied());
    }
    
    /**
     * Test method for {@link org.tonguetied.web.PreferenceForm#isTranslationFilterApplied()}.
     */
    @Test
    public final void testIsTranslationFilterAppliedWithLanguage()
    {
        PreferenceForm preferenceForm = new PreferenceForm();
        final Language language = new Language();
        preferenceForm.addLanguage(language);
        assertTrue(preferenceForm.isTranslationFilterApplied());
    }
}
