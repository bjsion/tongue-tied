package org.tonguetied.keywordmanagement;

import org.tonguetied.keywordmanagement.Language;
import org.tonguetied.keywordmanagement.Language.LanguageCode;

import junitx.extensions.ComparabilityTestCase;

/**
 * Test class to ensure the {@link Language} class is compliant with the 
 * <code>Comparable</code> interface.
 * 
 * @author bsion
 *
 */
public class LanguageComparabilityTest extends ComparabilityTestCase
{
    
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
    protected Comparable<Language> createEqualInstance() throws Exception {
        Language arabic = new Language();
        arabic.setCode(LanguageCode.ar);
        arabic.setName("Arabic");
        return arabic;
    }

    /* (non-Javadoc)
     * @see junitx.extensions.ComparabilityTestCase#createGreaterInstance()
     */
    @Override
    protected Comparable<Language> createGreaterInstance() throws Exception {
        Language russian = new Language();
        russian.setCode(LanguageCode.ru);
        russian.setName("Russian");
        return russian;
    }

    /* (non-Javadoc)
     * @see junitx.extensions.ComparabilityTestCase#createLessInstance()
     */
    @Override
    protected Comparable<Language> createLessInstance() throws Exception {
        Language defaultLanguage = new Language();
        defaultLanguage.setCode(LanguageCode.DEFAULT);
        defaultLanguage.setName("Default");
        return defaultLanguage;
    }

}
