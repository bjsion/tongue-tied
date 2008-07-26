package org.tonguetied.keywordmanagement;

import java.util.List;

import org.springframework.dao.DataAccessException;


/**
 * Interface defining DAO facade for TongueTied storage of the {@link Bundle}
 * object.
 * 
 * @author bsion
 */
public interface BundleRepository 
{
    /**
     * Persist a {@link Bundle} and if this bundle has been marked as the 
     * default bundle, then reset all other bundles so they are not the default.
     *   
     * @param bundle the bundle to save or update
     * @throws DataAccessException if the operation fails.
     */
    void saveOrUpdate(Bundle bundle) throws DataAccessException;
    
    /**
     * Remove a {@link Bundle} from permanent storage.
     * 
     * @param bundle the item to remove.
     */
    void delete(Bundle bundle);

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
    Bundle getBundleByName(final String name);
    
    /**
     * Retrieve the {@link Bundle} from permanent storage.
     * 
     * @param resourceName the unique name identifying the {@link Bundle}.
     * @return the {@link Bundle} matching the <code>name</code> or 
     * <code>null<code> if no match is found.
     */
    Bundle getBundleByResourceName(final String resourceName);
    
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
