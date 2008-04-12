package org.tonguetied.keywordmanagement;

import java.util.List;

import org.tonguetied.keywordmanagement.Country.CountryCode;
import org.tonguetied.keywordmanagement.Language.LanguageCode;

/**
 * Facade exposing business functions of the TongueTied.
 * 
 * @author bsion
 */
public interface KeywordService 
{
    /**
     * Get the {@link Keyword} matching the <code>id</code>.
     * 
     * @param id the unique identifier of the {@link Keyword}.
     * @return the {@link Keyword} matching the <code>id</code> or 
     * <code>null</code> if no match is found.
     */
    Keyword getKeyword(final Long id);
    
    /**
     * Get all the {@link Keyword}s in the system. This method applies no 
     * pagination.
     * 
     * @return all {@link Keyword}s in the system.
     */
    List<Keyword> getKeywords();
    
    /**
     * Get all the {@link Keyword}s in the system.
     * 
     * @param firstResult a row number, numbered from 0. If <code>null</code>
     * then then results begin at zero
     * @param maxResults the maximum number of rows. If <code>null</code> then
     * all results are returned
     * @return all {@link Keyword}s in the system.
     */
    List<Keyword> getKeywords(final Integer firstResult, final Integer maxResults);
    
    /**
     * Get the {@link Keyword} matching the <code>keywordString</code>.
     * 
     * @param keywordString the string uniquely identifying the {@link Keyword}
     * @return the {@link Keyword} matching the <code>keywordString</code> or 
     * <code>null</code> if no match is found.
     */
    Keyword getKeyword(final String keywordString);
    
    /**
     * Find all {@link Keyword}s whose matching the search criteria.
     * 
     * @param keyword the criteria to search for
     * @param ignoreCase <code>true</code> if case should be ignored. 
     * <code>false</code> otherwise
     * @param firstResult a row number, numbered from 0. If <code>null</code>
     * then then results begin at zero
     * @param maxResults the maximum number of rows. If <code>null</code> then
     * all results are returned
     * @return a list of all {@link Keyword}s matching the criteria
     */
    List<Keyword> findKeywords(Keyword keyword, 
                               final boolean ignoreCase,
                               final Integer firstResult,
                               final Integer maxResults);
    
    /**
     * Get the {@link Language} matching the <code>id</code>.
     * 
     * @param id the unique identifier of the {@link Language}.
     * @return the {@link Language} matching the <code>id</code> or 
     * <code>null</code> if no match is found.
     */
    Language getLanguage(final Long id);
    
    /**
     * Get all the {@link Language}s in the system.
     * 
     * @return all {@link Language}s in the system.
     */
    List<Language> getLanguages();

    /**
     * Get the {@link Language} matching the {@link LanguageCode}.
     * 
     * @param code the {@link LanguageCode} uniquely identifying the 
     * {@link Language}.
     * @return the {@link Language} matching the {@link LanguageCode} or 
     * <code>null</code> if no match is found.
     */
    Language getLanguage(final LanguageCode code);
    
    /**
     * Get the {@link Country} matching the <code>id</code>.
     * 
     * @param id the unique identifier of the {@link Country}.
     * @return the {@link Country} matching the <code>id</code> or 
     * <code>null</code> if no match is found.
     */
    Country getCountry(final Long id);
    
    /**
     * Get the {@link Country} matching the {@link CountryCode}.
     * 
     * @param code the {@link CountryCode} uniquely identifying the 
     * {@link Country}.
     * @return the {@link Country} matching the {@link CountryCode} or 
     * <code>null</code> if no match is found.
     */
    Country getCountry(final CountryCode code);
    
    /**
     * Get all the {@link Country}s in the system.
     * 
     * @return all {@link Country}s in the system.
     */
    List<Country> getCountries();
    
    /**
     * Get the {@link Bundle} matching the <code>id</code>.
     * 
     * @param id the unique identifier of the {@link Bundle}.
     * @return the {@link Bundle} matching the <code>id</code> or 
     * <code>null</code> if no match is found.
     */
    Bundle getBundle(final Long id);
    
    /**
     * Get the {@link Bundle} matching the <code>name</code>.
     * 
     * @param name the string uniquely identifying the {@link Bundle}
     * @return the {@link Bundle} matching the <code>name</code> or 
     * <code>null</code> if no match is found
     */
    Bundle getBundleByName(final String name);
    
    /**
     * Get the {@link Bundle} matching the <code>resourceName</code>.
     * 
     * @param resourceName the string uniquely identifying the {@link Bundle}
     * @return the {@link Bundle} matching the <code>name</code> or 
     * <code>null</code> if no match is found
     */
    Bundle getBundleByResourceName(final String resourceName);
    
    /**
     * Find and return the default {@link Bundle} if one exists.
     * 
     * @return the {@link Bundle} marked as the default or <code>null</code> if
     * no bundle is set as the default.  
     */
    Bundle getDefaultBundle();
    
    /**
     * Get all the {@link Bundle}s in the system.
     * 
     * @return all {@link Bundle}s in the system.
     */
    List<Bundle> getBundles();	
    
    /**
     * Save or update the object.
     * 
     * @param object the {@linkplain Object} to be persisted.
     */
    void saveOrUpdate(Object object);

    /**
     * Save or update the bundle.
     * 
     * @param bundle the {@linkplain Bundle} to be persisted.
     */
    void saveOrUpdate(Bundle bundle);
    
    /**
     * Remove the {@link Keyword} matching the <code>id</code>.
     * 
     * @param id the unique identifier of the {@link Keyword} to be removed.
     */
    void deleteKeyword(final Long id);

    /**
     * Remove the object.
     * 
     * @param object the {@linkplain Object} to be removed.
     */
    void delete(Object object);
}
