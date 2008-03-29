package org.tonguetied.web;

import java.beans.PropertyEditorSupport;

import org.apache.commons.lang.StringUtils;
import org.tonguetied.keywordmanagement.Translation.TranslationState;



/**
 * Support class to map enum {@link TranslationState} to a string key in the web
 * tier. This class is used for rendering purposes.
 * 
 * @author bsion
 *
 */
public class TranslationStateSupport extends PropertyEditorSupport {
	
    @Override
    public String getAsText() {
      Object value = getValue();
      return value == null ? "" : ((TranslationState)value).name();
    }

    @Override
    public void setAsText(String string) throws IllegalArgumentException {
        TranslationState value;
		
        if (StringUtils.isEmpty(string))
            value = null;
        else 
            value = TranslationState.valueOf(string);
        	
        setValue(value);
    }
}

