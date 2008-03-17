package org.tonguetied.domain;

import org.tonguetied.domain.Language.LanguageCode;

import junitx.extensions.ComparabilityTestCase;

public class LanguageComparabilityTest extends ComparabilityTestCase {
    
    /**
     * @param name
     */
    public LanguageComparabilityTest(String name) {
        super(name);
    }

    /* (non-Javadoc)
     * @see junitx.extensions.ComparabilityTestCase#createEqualInstance()
     */
    @Override
    protected Comparable<?> createEqualInstance() throws Exception {
        Language arabic = new Language();
        arabic.setCode(LanguageCode.ar);
        arabic.setName("Arabic");
        return arabic;
    }

    /* (non-Javadoc)
     * @see junitx.extensions.ComparabilityTestCase#createGreaterInstance()
     */
    @Override
    protected Comparable<?> createGreaterInstance() throws Exception {
        Language russian = new Language();
        russian.setCode(LanguageCode.ru);
        russian.setName("Russian");
        return russian;
    }

    /* (non-Javadoc)
     * @see junitx.extensions.ComparabilityTestCase#createLessInstance()
     */
    @Override
    protected Comparable<?> createLessInstance() throws Exception {
        Language defaultLanguage = new Language();
        defaultLanguage.setCode(LanguageCode.DEFAULT);
        defaultLanguage.setName("Default");
        return defaultLanguage;
    }

}
