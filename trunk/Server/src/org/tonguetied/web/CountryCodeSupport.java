package org.tonguetied.web;

import java.beans.PropertyEditorSupport;

import org.apache.commons.lang.StringUtils;
import org.tonguetied.domain.Country.CountryCode;



/**
 * Support class to map enum {@link CountryCode} to a string key in the web
 * tier. This class is used for rendering purposes.
 * 
 * @author bsion
 *
 */
public class CountryCodeSupport extends PropertyEditorSupport {
	
    @Override
    public String getAsText() {
      Object value = getValue();
      return value == null ? "" : ((CountryCode)value).name();
    }

    @Override
    public void setAsText(String string) throws IllegalArgumentException {
        CountryCode value;
		
        if (StringUtils.isEmpty(string))
            value = null;
        else 
            value = CountryCode.valueOf(string);
        	
        setValue(value);
    }
}

