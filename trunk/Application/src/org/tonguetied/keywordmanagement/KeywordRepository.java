package org.tonguetied.keywordmanagement;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.tonguetied.keywordmanagement.Country.CountryCode;
import org.tonguetied.keywordmanagement.Language.LanguageCode;


/**
 * Interface defining DAO facade for TongueTied storage.
 * 
 * @author mforslund
 */
public interface KeywordRepository 
{
    /**
     * Persist an object to permanent storage.
     * 
     * @param object the item to save or update.
     * @throws DataAccessException if the operation fails.
     */
    void saveOrUpdate(Object object) throws DataAccessException;
    
    /**
     * Persist a {@link Bundle} and if this bundle has been marked as the 
     * default bundle, then reset all other bundles so they are not the default.
     *   
     * @param bundle the bundle to save or update
     * @throws DataAccessException if the operation fails.
     */
    void saveOrUpdate(Bundle bundle) throws DataAccessException;
    
    /**
     * Remove an object from permanent storage.
     * 
     * @param object the item to remove.
     */
    void delete(Object object);

    /**
     * Retrieve the {@link Keyword} from permanent storage.
     * 
     * @param id the unique identifier of the {@link Keyword}.
     * @return the {@link Keyword} matching the <code>id</code> or 
     * <code>null<code> if no match is found.
     */
    Keyword getKeyword(final Long id);
    
    /**
     * Retrieve the {@link Keyword} from permanent storage.
     * 
     * @param keywordString the unique name of the {@link Keyword}.
     * @return the {@link Keyword} matching the <code>keywordString</code> or 
     * <code>null<code> if no match is found.
     */
    Keyword getKeyword(final String keywordString);
    
    /**
     * Retrieve all {@link Keyword}s from permanent storage.
     * 
     * @param firstResult a row number, numbered from 0. If <code>null</code>
     * then then results begin at zero
     * @param maxResults the maximum number of rows. If <code>null</code> then
     * all results are returned
     * @return the {@link List} of all {@link Keyword}s in the system.
     */
    List<Keyword> getKeywords(final Integer firstResult,
                              final Integer maxResults);

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
     * @return a list of all keywords matching the criteria
     * @throws IllegalArgumentException if the keyword is <code>null</code>
     * @throws IllegalArgumentException if the matchMode is <code>null</code>
     */
    List<Keyword> findKeywords(Keyword keyword, 
                               final boolean ignoreCase,
                               final Integer firstResult,
                               final Integer maxResults)
            throws IllegalArgumentException;
    
    /**
     * Retrieve the {@link Language} from permanent storage.
     * 
     * @param id the unique identifier of the {@link Language}.
     * @return the {@link Language} matching the <code>id</code> or 
     * <code>null<code> if no match is found.
     */
    Language getLanguage(final Long id);

    /**
     * Retrieve the {@link Language} from permanent storage.
     * 
     * @param code the unique {@link LanguageCode} identifying the 
     * {@link Language}.
     * @return the {@link Language} matching the <code>id</code> or 
     * <code>null<code> if no match is found.
     */
    Language getLanguage(final LanguageCode code);
    
    /**
     * Retrieve all {@link Language}s from permanent storage.
     * 
     * @return the {@link List} of all {@link Language}s in the system.
     */
    List<Language> getLanguages();

    /**
     * Retrieve the {@link Country} from permanent storage.
     * 
     * @param id the unique identifier of the {@link Country}.
     * @return the {@link Country} matching the <code>id</code> or 
     * <code>null<code> if no match is found.
     */
    Country getCountry(final Long id);

    /**
     * Retrieve the {@link Country} from permanent storage.
     * 
     * @param code the unique {@link CountryCode} identifying the {@link Country}.
     * @return the {@link Country} matching the <code>id</code> or 
     * <code>null<code> if no match is found.
     */
    Country getCountry(final CountryCode code);
    
    /**
     * Retrieve all {@link Country}s from permanent storage.
     * 
     * @return the {@link List} of all {@link Country}s in the system.
     */
    List<Country> getCountries();
    
    /**
     * Retrieve the {@link Bundle} from permanent storage.
     * 
     * @param id the unique identifier of the {@link Bundle}.
     * @return the {@link Bundle} matching the <code>id</code> or 
     * <code>null<code> if no match is found.
     */
    Bundle getBundle(final Long id);

    /**
     * Retrieve the {@link Bundle} from permanent storage.
     * 
     * @param name the unique name identifying the {@link Bundle}.
     * @return the {@link Bundle} matching the <code>name</code> or 
     * <code>null<code> if no match is found.
     */
    Bundle getBundle(final String name);
    
    /**
     * Retrieve the {@link Bundle} marked as the default from permanent 
     * storage.
     * 
     * @return the default {@link Bundle} or <code>null</code> if one is not set
     */
    Bundle getDefaultBundle();
    
    /**
     * Retrieve all {@link Bundle}s from permanent storage.
     * 
     * @return the {@link List} of all {@link Bundle}s in the system.
     */
    List<Bundle> getBundles();	  
}
