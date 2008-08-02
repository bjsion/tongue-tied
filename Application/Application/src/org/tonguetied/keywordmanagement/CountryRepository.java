package org.tonguetied.keywordmanagement;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.tonguetied.keywordmanagement.Country.CountryCode;


/**
 * Interface defining the DAO facade for the Country in TongueTied storage.
 * 
 * @author bsion
 */
public interface CountryRepository 
{
    /**
     * Persist an <code>Country</code> to permanent storage.
     * 
     * @param country the item to save or update.
     * @throws DataAccessException if the operation fails.
     */
    void saveOrUpdate(Country country) throws DataAccessException;
    
    /**
     * Remove a <code>Country</code> from permanent storage.
     * 
     * @param country the item to remove.
     */
    void delete(Country country);

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
}
