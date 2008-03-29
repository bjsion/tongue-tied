package org.tonguetied.web;

import java.beans.PropertyEditorSupport;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.tonguetied.keywordmanagement.Language;


/**
 * Support class to map enum {@link Language} to a string key in the web tier. 
 * This class is used for rendering purposes.
 * 
 * @author bsion
 *
 */
public class LanguageSupport extends PropertyEditorSupport {
    private Map<Long, Language> languages;

    /**
     * Create new instance of LanguageSupport.
     * 
     * @param l the list of current {@link Language}s  
     */
    public LanguageSupport(List<Language> l) {
        languages = new HashMap<Long, Language>();
        for (Language language: l) {
            languages.put(language.getId(), language);
        }
    }

    @Override
    public String getAsText() {
      Object value = getValue();
      return value == null ? "" : ((Language)value).getId().toString();
    }

    @Override
    public void setAsText(String string) throws IllegalArgumentException {
        Language value = null;
                
        if (StringUtils.isNotEmpty(string))
        {
            Long id = Long.valueOf(string);
            if (languages.containsKey(id)) {
                value = languages.get(id);
            }
            else {
                throw new IllegalArgumentException("Language with id " 
                        + string + " not found");
            }
        }
        
        setValue(value);
    }
}
