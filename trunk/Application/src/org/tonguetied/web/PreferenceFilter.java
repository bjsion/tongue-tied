package org.tonguetied.web;


import org.apache.commons.collections.Predicate;
import org.tonguetied.domain.Bundle;
import org.tonguetied.domain.Translation;


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
