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


import org.apache.commons.collections.Predicate;
import org.tonguetied.keywordmanagement.Bundle;
import org.tonguetied.keywordmanagement.Translation;


/**
 * Filter used to determine if a {@link Translation} matches the preferences
 * set in the {@link PreferenceForm}. 
 * 
 * @author bsion
 *
 */
public class PreferenceFilter implements Predicate {

    private PreferenceForm preferences;
    
    /**
     * Create a new instance of the <code>PreferenceFilter</code>.
     * 
     * @param preferences the {@link PreferenceForm} used to evaluate if a 
     * {@link Translation} should be filter away or not 
     */
    public PreferenceFilter(PreferenceForm preferences) {
        this.preferences = preferences;
    }

    /* (non-Javadoc)
     * @see org.apache.commons.collections.Predicate#evaluate(java.lang.Object)
     */
    public boolean evaluate(Object object) {
        Translation translation = (Translation) object;
        
        boolean result = false;
        
        if (translation != null) {
            result = 
                preferences.getSelectedCountries().contains(translation.getCountry())
                &&
                isValidBundle(translation.getBundle())
                && 
                preferences.getSelectedLanguages().contains(translation.getLanguage());
        }
        
        return result;  
    }
    
    /**
     * Determine if the bundle of a {@link Translation} is in the list of 
     * {@link Bundle}s. If the bundle is <code>null</code> then returns 
     * <code>true</code>.
     * 
     * @param bundle the Bundle of the translation to evaluate 
     * @return <code>true</code> if the bundle is valid, <code>false</code>
     * otherwise
     */
    private boolean isValidBundle(Bundle bundle) {
        boolean result = false;
        
        if (bundle == null) {
            result = true;
        }
        else {
            result = preferences.getSelectedBundles().contains(bundle);
        }
        
        return result;
    }
}
