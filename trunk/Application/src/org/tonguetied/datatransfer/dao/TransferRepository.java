package org.tonguetied.datatransfer.dao;

import java.util.List;

import org.tonguetied.datatransfer.common.ExportParameters;
import org.tonguetied.keywordmanagement.Country;
import org.tonguetied.keywordmanagement.Translation;
import org.tonguetied.keywordmanagement.Country.CountryCode;


/**
 * Interface defining Keyword DAO facade for TongueTied storage.
 * 
 * @author mforslund
 */
public interface TransferRepository 
{
    /**
     * Retrieve the {@link Country} from permanent storage.
     * 
     * @param code the unique {@link CountryCode} identifying the {@link Country}.
     * @return the {@link Country} matching the <code>id</code> or 
     * <code>null<code> if no match is found.
     */
    Country getCountry(final CountryCode code);
    

    /**
     * Execute a report query to locate all {@link Translation}s that match the
     * export criteria specified in the {@link ExportParameters}.
     * 
     * @param parameters set of attributes used to filter result set
     * @return all {@link Translation}s that match the search criteria.
     */
    List<Translation> findTranslations(ExportParameters parameters);
}
