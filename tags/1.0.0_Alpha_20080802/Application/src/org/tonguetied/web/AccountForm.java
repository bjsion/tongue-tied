package org.tonguetied.web;

import org.tonguetied.usermanagement.User;

/**
 * Value object for the user to make changes to their account details. This
 * object will only contain the fields that a User can update in the 
 * {@link User} object. This is done for security reasons.
 * 
 * @author bsion
 *
 */
public class AccountForm
{
    private String firstName;
    private String lastName;
    private String email;
    
    /**
     * Create a new instance of AccountForm.
     */
    public AccountForm()
    {
    }
    
    /**
     * Create a new instance of AccountForm.
     * 
     * @param firstName the user's first name
     * @param lastName the user's surname
     * @param email the user's email
     */
    public AccountForm(final String firstName, 
            final String lastName, 
            final String email)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    /**
     * @return the firstName
     */
    public String getFirstName()
    {
        return firstName;
    }
    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(final String firstName)
    {
        this.firstName = firstName;
    }
    /**
     * @return the lastName
     */
    public String getLastName()
    {
        return lastName;
    }
    /**
     * @param lastName the lastName to set
     */
    public void setLastName(final String lastName)
    {
        this.lastName = lastName;
    }
    /**
     * @return the email
     */
    public String getEmail()
    {
        return email;
    }
    /**
     * @param email the email to set
     */
    public void setEmail(final String email)
    {
        this.email = email;
    }
}
