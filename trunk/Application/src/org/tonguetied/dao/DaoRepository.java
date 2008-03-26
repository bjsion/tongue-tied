package org.tonguetied.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.tonguetied.domain.AuditLogRecord;
import org.tonguetied.domain.Bundle;
import org.tonguetied.domain.Country;
import org.tonguetied.domain.ExportParameters;
import org.tonguetied.domain.Keyword;
import org.tonguetied.domain.Language;
import org.tonguetied.domain.Translation;
import org.tonguetied.domain.User;
import org.tonguetied.domain.Country.CountryCode;
import org.tonguetied.domain.Language.LanguageCode;


/**
 * Interface defining DAO facade for TongueTied storage.
 * 
 * @author mforslund
 */
public interface DaoRepository 
{
    /**
     * Persist an object to permanent storage.
     * 
     * @param object the item to save or update.
     * @throws DataAccessException if the operation fails.
     */
    void saveOrUpdate(Object object) throws DataAccessException;
    
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
     * Execute a report query to locate all {@link Translation}s that match the
     * export criteria specified in the {@link ExportParameters}.
     * 
     * @param parameters set of attributes used to filter result set
     * @return all {@link Translation}s that match the search criteria.
     */
    List<Translation> findTranslations(ExportParameters parameters);

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
     * Retrieve all {@link Bundle}s from permanent storage.
     * 
     * @return the {@link List} of all {@link Bundle}s in the system.
     */
    List<Bundle> getBundles();	  
    
    /**
     * Retrieve the user by business key, ie the {@link User#getUsername()} 
     * from permanent storage.
     * 
     * @param username the business key to search for
     * @return the {@link User} matching the <code>username</code> or 
     * <code>null</code> if no match is found
     */
    User getUser(final String username);
    
    /**
     * Retrieve the user by the unique identifier from permanent storage.
     * 
     * @param id the unique key to search for
     * @return the {@link User} matching the <code>id</code> or 
     * <code>null</code> if no match is found
     */
    User getUser(final Long id);
    
    /**
     * Retrieve a set of all {@link User}s from permanent storage.
     * 
     * @return a set of all {@link User}s in the system
     */
    List<User> getUsers();

    /**
     * Retrieve a list of all {@link AuditLogRecord}s from permanent storage. 
     * The return list will be ordered in reverse chronological order.
     * 
     * @return a list of all {@link AuditLogRecord} in the system
     */
    List<AuditLogRecord> getAuditLog();
}
