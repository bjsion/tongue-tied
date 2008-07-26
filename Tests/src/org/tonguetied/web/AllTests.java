package org.tonguetied.web;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suite.
 * 
 * @author bsion
 *
 */
@RunWith(Suite.class)
@SuiteClasses({
    AccountFormValidatorTest.class,
    BundleSupportTest.class,
    BundleValidatorTest.class,
    ChangePasswordFormValidatorTest.class,
    CountryCodeSupportTest.class,
    CountrySupportTest.class,
    CountryValidatorTest.class,
    ExportParametersValidatorTest.class,
    FormatTypeSupportTest.class,
    ImportValidatorTest.class,
    KeywordValidatorTest.class,
    LanguageCodeSupportTest.class,
    LanguageSupportTest.class,
    LanguageValidatorTest.class,
    PermissionSupportTest.class,
    PreferenceFilterTest.class,
    PreferenceFormEqualsHashCodeTest.class,
    SearchFormTest.class,
    TranslationStateSupportTest.class,
    TranslationTransformerTest.class,
    UserValidatorTest.class
})
public class AllTests {

    public static Test suite() {
        return new JUnit4TestAdapter(AllTests.class);
    }
}
