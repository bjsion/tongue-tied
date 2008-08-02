package org.tonguetied.keywordmanagement;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.tonguetied.keywordmanagement.Language.LanguageCode;


/**
 * Interface defining the DAO facade for the Language in TongueTied storage.
 * 
 * @author bsion
 */
public interface LanguageRepository 
{
    /**
     * Persist an {@link Language} to permanent storage.
     * 
     * @param language the item to save or update.
     * @throws DataAccessException if the operation fails.
     */
    void saveOrUpdate(Language language) throws DataAccessException;
    
    /**
     * Remove an {@link Language} from permanent storage.
     * 
     * @param language the item to remove.
     */
    void delete(Language language);

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
}
