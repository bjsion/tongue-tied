package org.tonguetied.usermanagement;

import java.util.List;

import org.springframework.dao.DataAccessException;


/**
 * Interface defining User DAO facade for TongueTied storage.
 * 
 * @author mforslund
 */
public interface UserRepository 
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
}
