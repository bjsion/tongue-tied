package org.tonguetied.usermanagement;

/**
 * Exception indicating that the user cannot be authenticated.
 * 
 * @author bsion
 *
 */
public class AuthenticationException extends RuntimeException 
{
    private static final long serialVersionUID = 8649831549679257594L;

    /**
     * Create a new instance of AuthenticationException.
     * 
     * @param message TODO
     */
    public AuthenticationException(String message) {
        super(message);
    }

}
