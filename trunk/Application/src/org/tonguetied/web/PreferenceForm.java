package org.tonguetied.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.tonguetied.domain.Bundle;
import org.tonguetied.domain.Country;
import org.tonguetied.domain.Language;
import org.tonguetied.service.ApplicationService;


/**
 * Pojo to store the users viewing preferences.
 * 
 * @author bsion
 *
 */
public class PreferenceForm {
    private List<Bundle> selectedBundles;
    private List<Country> selectedCountries;
    private List<Language> selectedLanguages;
    private int maxResults;
    private ApplicationService appService; 
    
    /**
     * Create a new instance of the PreferenceForm.
     *
     */
    public PreferenceForm() {
        this.selectedLanguages = new ArrayList<Language>();
        this.selectedCountries = new ArrayList<Country>();
        this.selectedBundles = new ArrayList<Bundle>();
    }
    
    /**
     * Return the list of {@link Bundle}s selected by the user to view.
     * 
     * @return a list of selected {@link Bundle}s.
     */
    public List<Bundle> getSelectedBundles() {
        return selectedBundles;
    }

    /**
     * Set the list of {@link Bundle}s to view. 
     * 
     * @param selectedBundles the list of {@link Bundle}s to set.
     */
    public void setSelectedBundles(List<Bundle> selectedBundles) {
        this.selectedBundles = selectedBundles;
    }

    /**
     * Add a new {@link Bundle} to the list of view able {@link Bundle}s.
     * 
     * @param bundle the {@link Bundle} to add.
     */
    public void addBundle(Bundle bundle) {
        this.selectedBundles.add(bundle);
    }

    /**
     * Return the list of {@link Country}s selected by the user to view.
     * 
     * @return a list of selected {@link Country}s.
     */
    public List<Country> getSelectedCountries() {
        return selectedCountries;
    }

    /**
     * Set the list of {@link Country}s to view. 
     * 
     * @param selectedCountries the list of {@link Country}s to set.
     */
    public void setSelectedCountries(List<Country> selectedCountries) {
        this.selectedCountries = selectedCountries;
    }

    /**
     * Add a new {@link Country} to the list of view able {@link Country}s.
     * 
     * @param country the {@link Country} to add.
     */
    public void addCountry(Country country) {
        this.selectedCountries.add(country);
    }

    /**
     * Return the list of {@link Language}s selected by the user to view.
     * 
     * @return a list of selected {@link Language}s.
     */
    public List<Language> getSelectedLanguages() {
        return selectedLanguages;
    }
    
    /**
     * Set the list of {@link Language}s to view. 
     * 
     * @param selectedLanguages the list of {@link Language}s to set.
     */
    public void setSelectedLanguages(List<Language> selectedLanguages) {
        this.selectedLanguages = selectedLanguages;
    }
    
    /**
     * Add a new {@link Language} to the list of view able {@link Language}s.
     * 
     * @param language the {@link Language} to add.
     */
    public void addLanguage(Language language) {
        this.selectedLanguages.add(language);
    }

    /**
     * @return the maxResults
     */
    public int getMaxResults() {
        return maxResults;
    }

    /**
     * @param maxResults the maxResults to set
     */
    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }

    /**
     * Method used to initialize class after it has been created. After 
     * initialization each list has its default item added.
     */
    public void init() {
        this.setSelectedLanguages(appService.getLanguages());
        this.setSelectedCountries(appService.getCountries());
        this.setSelectedBundles(appService.getBundles());
        this.maxResults = 20;
    }
    
    /**
     * Assign the {@link ApplicationService}.
     * 
     * @param appService the {@link ApplicationService} to set.
     */
    public void setAppService(ApplicationService appService) {
        this.appService = appService;
    }
    
    @Override
    public boolean equals(Object obj) {
        boolean isEqual = false;
        // a good optimization
        if (this == obj)
        {
            isEqual = true;
        }
        else if (obj instanceof PreferenceForm)
        {
            PreferenceForm other = (PreferenceForm)obj;
            
            isEqual = this.maxResults == other.maxResults
                      && (this.selectedCountries == null? 
                            other.selectedCountries== null: 
                            selectedCountries.equals(other.selectedCountries))
                      && (this.selectedLanguages == null? 
                            other.selectedLanguages == null: 
                            selectedLanguages.equals(other.selectedLanguages));
        }
        
        return isEqual;
    }
    
    @Override
    public int hashCode() {
        int result = 11;
        
        result = 19 * result + maxResults;
        result = 19 * result + 
            (this.selectedCountries == null? 0: this.selectedCountries.hashCode());
        result = 19 * result + 
            (this.selectedLanguages == null? 0: this.selectedLanguages.hashCode());

        return result;
    }
    
    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this, 
                ToStringStyle.SHORT_PREFIX_STYLE).toString();
    }
}
