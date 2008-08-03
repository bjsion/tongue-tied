package org.tonguetied.usermanagement;

import java.util.List;


/**
 * This interface defines the events used for user management.
 * 
 * @author bsion
 *
 */
public interface UserService {

    /**
     * Create or update a new user in the system.
     * 
     * @param user the {@link User} to persist
     */
    void saveOrUpdate(User user);
    
    /**
     * Retrieve the user by business key, ie the {@link User#getUsername()}.
     * 
     * @param username the business key to search for
     * @return the {@link User} matching the <code>username</code> or 
     * <code>null</code> if no match is found
     */
    User getUser(final String username);
    
    /**
     * Retrieve the user by the unique identifier.
     * 
     * @param id the unique key to search for
     * @return the {@link User} matching the <code>id</code> or 
     * <code>null</code> if no match is found
     */
    User getUser(final Long id);
    
    /**
     * Retrieve a list of all {@link User}s.
     * 
     * @return a list of all {@link User}s in the system
     */
    List<User> getUsers();
    
    /**
     * Find all {@link User}s that match the criteria specified. This method
     * should allow wild card searches on the user attributes.
     * 
     * @param user the object holding the attributes of the search 
     * criteria
     * @return a collection of {@link User} objects matching the search 
     * criteria
     */
    List<User> findUsers(final User user);
    
    /**
     * Encode the new password and persist the changes to the {@link User}.
     * 
     * @param user the user to update
     * @param oldPassword the old raw value of this User's password to be 
     * validated
     * @param newPassword the new raw value of the password
     * @throws AuthenticationException if an invalid <code>oldPassword</code>
     * is supplied
     * @throws IllegalArgumentException if the new password is <code>null</code>
     */
    void changePassword(User user, final String oldPassword, final String newPassword) 
        throws AuthenticationException;
    
    /**
     * Encryption the password. The method of encryption is determined by the
     * implementing class.
     * 
     * @param user the {@link User} to apply the new password to
     * @param newPassword the raw value of the new password
     */
    void encodePassword(User user, final String newPassword);
}
