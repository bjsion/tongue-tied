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
     * Persist a {@link User} object to permanent storage.
     * 
     * @param user the user to save or update.
     * @throws DataAccessException if the operation fails.
     */
    void saveOrUpdate(User user) throws DataAccessException;
    
    /**
     * Remove a {@link User} object from permanent storage.
     * 
     * @param user the item to remove.
     */
    void delete(User user);

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
