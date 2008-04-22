package org.tonguetied.web;

import java.beans.PropertyEditorSupport;

import org.apache.commons.lang.StringUtils;
import org.tonguetied.datatransfer.common.FormatType;


/**
 * Support class to map enum {@link FormatType} to a string key in the web tier.
 * This class is used for rendering purposes.
 * 
 * @author bsion
 *
 */
public class FormatTypeSupport extends PropertyEditorSupport {

    @Override
    public String getAsText() {
      Object value = getValue();
      return value == null ? "" : ((FormatType)value).toString();
    }

    @Override
    public void setAsText(String string) throws IllegalArgumentException {
        FormatType value;
                
        if (StringUtils.isEmpty(string))
            value = null;
        else
            value = FormatType.valueOf(string);
        
        setValue(value);
    }
}
