package org.tonguetied.keywordmanagement;

import java.util.List;

import org.springframework.dao.DataAccessException;


/**
 * Interface defining the DAO facade for the Keyword aggregation in TongueTied 
 * storage.
 * 
 * @author bsion
 */
public interface KeywordRepository 
{
    /**
     * Persist a {@link Keyword} to permanent storage.
     * 
     * @param keyword the keyword item to save or update.
     * @throws DataAccessException if the operation fails.
     */
    void saveOrUpdate(Keyword keyword) throws DataAccessException;
    
    /**
     * Remove an {@link Keyword} from permanent storage.
     * 
     * @param keyword the keyword item to remove.
     */
    void delete(Keyword keyword);

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
}
