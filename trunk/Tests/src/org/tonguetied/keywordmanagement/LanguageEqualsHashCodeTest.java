package org.tonguetied.keywordmanagement;

import org.tonguetied.keywordmanagement.Language;
import org.tonguetied.keywordmanagement.Language.LanguageCode;

import junitx.extensions.EqualsHashCodeTestCase;


/**
 * @author bsion
 *
 */
public class LanguageEqualsHashCodeTest extends EqualsHashCodeTestCase {

    public LanguageEqualsHashCodeTest(String name) {
        super(name);
    }

    @Override
    protected Object createInstance() throws Exception {
        Language language = new Language();
        language.setCode(LanguageCode.en);
        language.setName("English");
        
        return language;
    }

    @Override
    protected Object createNotEqualInstance() throws Exception {
        Language language = new Language();
        language.setCode(LanguageCode.de);
        language.setName("German");
        
        return language;
    }
}
