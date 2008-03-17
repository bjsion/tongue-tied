package org.tonguetied.web;

import org.tonguetied.domain.Language;
import org.tonguetied.domain.Language.LanguageCode;

import junitx.extensions.EqualsHashCodeTestCase;


/**
 * @author bsion
 *
 */
public class PreferenceFormEqualsHashCodeTest extends EqualsHashCodeTestCase {

    public PreferenceFormEqualsHashCodeTest(String name) {
        super(name);
    }

    @Override
    protected Object createInstance() throws Exception {
        PreferenceForm preferences = new PreferenceForm();
        
        Language language = new Language();
        language.setCode(LanguageCode.fr);
        language.setName("French");
        preferences.addLanguage(language);
        
        return preferences;
    }

    @Override
    protected Object createNotEqualInstance() throws Exception {
        PreferenceForm preferences = new PreferenceForm();
        
        Language language = new Language();
        language.setCode(LanguageCode.es);
        language.setName("Spanish");
        preferences.addLanguage(language);
        
        return preferences;
    }
}
