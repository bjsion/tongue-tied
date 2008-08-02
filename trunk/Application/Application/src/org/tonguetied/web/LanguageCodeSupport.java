package org.tonguetied.web;

import java.beans.PropertyEditorSupport;

import org.apache.commons.lang.StringUtils;
import org.tonguetied.keywordmanagement.Language.LanguageCode;


/**
 * Support class to map enum {@link LanguageCode} to a string key in the 
 * web tier. This class is used for rendering purposes.
 * 
 * @author bsion
 *
 */
public class LanguageCodeSupport extends PropertyEditorSupport {

    @Override
    public String getAsText() {
      Object value = getValue();
      return value == null ? "" : ((LanguageCode)value).toString();
    }

    @Override
    public void setAsText(String string) throws IllegalArgumentException {
        LanguageCode value;
                
        if (StringUtils.isEmpty(string))
            value = null;
        else
            value = LanguageCode.valueOf(string);
        
        setValue(value);
    }
}
