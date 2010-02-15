/*
 * Copyright 2008 The Tongue-Tied Authors
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not 
 * use this file except in compliance with the License. You may obtain a copy 
 * of the License at
 *  
 * http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the 
 * License for the specific language governing permissions and limitations 
 * under the License. 
 */
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
    CookieUtilsTest.class,
    CountryCodeSupportTest.class,
    CountrySupportTest.class,
    CountryValidatorTest.class,
    EmptyPreferenceFilterTest.class,
    ExportParametersValidatorTest.class,
    FileBeanTest.class,
    FormatTypeSupportTest.class,
    ImportValidatorTest.class,
    KeywordTranslationValidatorTest.class,
    KeywordValidatorTest.class,
    LanguageCodeSupportTest.class,
    LanguageSupportTest.class,
    LanguageValidatorTest.class,
    PaginationUtilsTest.class,
    PermissionSupportTest.class,
    PreferenceFilterTest.class,
    PreferenceFormEqualsHashCodeTest.class,
    PreferenceFormTest.class,
    RequestUtilsTest.class,
    SearchFormTest.class,
    TranslationStateSupportTest.class,
    UserValidatorTest.class
})
public class AllTests {

    public static Test suite() {
        return new JUnit4TestAdapter(AllTests.class);
    }
}
