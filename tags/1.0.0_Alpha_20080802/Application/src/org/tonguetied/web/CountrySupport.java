package org.tonguetied.web;

import java.beans.PropertyEditorSupport;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.tonguetied.keywordmanagement.Country;


/**
 * Support class to map enum {@link Country} to a string key in the web tier.
 * This class is used for rendering purposes.
 * 
 * @author bsion
 *
 */
public class CountrySupport extends PropertyEditorSupport {
    private Map<Long, Country> countries;

    /**
     * Create new instance of CountrySupport.
     * 
     * @param c the list of current {@link Country}s  
     */
    public CountrySupport(List<Country> c) {
        this.countries = new HashMap<Long, Country>();
        for (Country country: c) {
            this.countries.put(country.getId(), country);
        }
    }

    @Override
    public String getAsText() {
      Object value = getValue();
      return value == null ? "" : ((Country)value).getId().toString();
    }

    @Override
    public void setAsText(String string) throws IllegalArgumentException {
        Country value = null;
                
        if (StringUtils.isNotEmpty(string))
        {
            Long id = Long.valueOf(string);
            if (countries.containsKey(id)) {
                value = countries.get(id);
            }
            else {
                throw new IllegalArgumentException("Country with id " 
                        + string + " not found");
            }
        }
        
        setValue(value);
    }
}
