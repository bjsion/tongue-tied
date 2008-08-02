package org.tonguetied.web;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Value object used to pass the parameters for a change password request by a
 * user.
 * 
 * @author bsion
 *
 */
public class ChangePasswordForm
{
    private String oldPassword;
    private String newPassword;
    private String newRepeatedPassword;
    
    /**
     * @return the oldPassword
     */
    public String getOldPassword() {
        return oldPassword;
    }

    /**
     * @param oldPassword the oldPassword to set
     */
    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    /**
     * @return the newPassword
     */
    public String getNewPassword() {
        return newPassword;
    }

    /**
     * @param newPassword the newPassword to set
     */
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    /**
     * @return the newRepeatedPassword
     */
    public String getNewRepeatedPassword() {
        return newRepeatedPassword;
    }

    /**
     * @param newRepeatedPassword the newRepeatedPassword to set
     */
    public void setNewRepeatedPassword(String newRepeatedPassword) {
        this.newRepeatedPassword = newRepeatedPassword;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).
            append("password", "********").
            toString();
    }

}
