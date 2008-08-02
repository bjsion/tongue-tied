package org.tonguetied.keywordmanagement;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * @author bsion
 *
 */
@RunWith(Suite.class)
@SuiteClasses({
    BundleComparabilityTest.class,
    BundleEqualsHashCodeTest.class,
    BundlePersistenceTest.class,
    CountryComparabilityTest.class,
    CountryEqualsHashCodeTest.class,
    CountryPersistenceTest.class,
    KeywordByLanguageEqualsHashCodeTest.class,
    KeywordEqualsHashCodeTest.class,
    KeywordFactoryTest.class,
    KeywordPersistenceTest.class,
    KeywordServiceTest.class,
    KeywordTest.class,
    LanguageComparabilityTest.class,
    LanguageEqualsHashCodeTest.class,
    LanguagePersistenceTest.class,
    TranslationComparabilityTest.class,
    TranslationEqualsHashCodeTest.class,
    TranslationPersistenceTest.class,
    TranslationTest.class
})
public class AllTests {

    public static Test suite() {
        return new JUnit4TestAdapter(AllTests.class);
    }
}
