package org.tonguetied.web;

import java.beans.PropertyEditorSupport;

import org.apache.commons.lang.StringUtils;
import org.tonguetied.usermanagement.UserRight.Permission;


/**
 * Support class to map enum {@link Permission} to a string key in the 
 * web tier. This class is used for rendering purposes.
 * 
 * @author bsion
 *
 */
public class PermissionSupport extends PropertyEditorSupport {

    @Override
    public String getAsText() {
      Object value = getValue();
      return value == null ? "" : ((Permission)value).toString();
    }

    @Override
    public void setAsText(String string) throws IllegalArgumentException {
        Permission value;
                
        if (StringUtils.isEmpty(string))
            value = null;
        else
            value = Permission.valueOf(string);
        
        setValue(value);
    }
}
