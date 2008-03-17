package org.tonguetied.web;

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
    BundleSupportTest.class,
    BundleValidatorTest.class,
    CountryCodeSupportTest.class,
    CountrySupportTest.class,
    CountryValidatorTest.class,
    ExportParametersValidatorTest.class,
    FormatTypeSupportTest.class,
    KeywordValidatorTest.class,
    LanguageCodeSupportTest.class,
    LanguageSupportTest.class,
    LanguageValidatorTest.class,
    PermissionSupportTest.class,
    PreferenceFilterTest.class,
    PreferenceFormEqualsHashCodeTest.class,
    TranslationStateSupportTest.class,
    TranslationTransformerTest.class,
    UserValidatorTest.class
})
public class AllTests {

    public static Test suite() {
        return new JUnit4TestAdapter(AllTests.class);
    }
}
