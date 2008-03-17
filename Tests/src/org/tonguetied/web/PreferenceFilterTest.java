package org.tonguetied.web;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.tonguetied.domain.Bundle;
import org.tonguetied.domain.Country;
import org.tonguetied.domain.Keyword;
import org.tonguetied.domain.Language;
import org.tonguetied.domain.Translation;
import org.tonguetied.domain.Country.CountryCode;
import org.tonguetied.domain.Language.LanguageCode;


/**
 * @author bsion
 *
 */
@RunWith(value=Parameterized.class)
public class PreferenceFilterTest {
    
    private boolean expected;
    private Translation translation;
    
    private PreferenceForm preferences;
    
    private static Country china;
    private static Country newZealand;
    private static Language english;
    private static Language spanish;
    private static Bundle bundle1;
    private static Bundle bundle2;

    /**
     * @param expected
     * @param value
     * @param language
     * @param country
     * @param bundle
     * @param keyword
     */
    public PreferenceFilterTest(boolean expected, String value, Language language, Country country, Bundle bundle, String keywordStr) {
        this.expected = expected;
        if (value == null && keywordStr == null) {
            this.translation = null;
        }
        else {
            this.translation = new Translation();
            translation.setValue(value);
            translation.setLanguage(language);
            translation.setCountry(country);
            translation.setBundle(bundle);
            Keyword keyword = new Keyword();
            keyword.setKeyword(keywordStr);
            keyword.addTranslation(translation);
            translation.setKeyword(keyword);
        }
    }

    @Parameters
    public static final Collection<Object[]> data() {
        china = new Country();
        china.setCode(CountryCode.CN);
        china.setName("China");
        newZealand = new Country();
        newZealand.setCode(CountryCode.NZ);
        newZealand.setName("New Zealand");
        
        english = new Language();
        english.setCode(LanguageCode.en);
        english.setName("English");
        spanish = new Language();
        spanish.setCode(LanguageCode.es);
        spanish.setName("Spanish");
        
        bundle1 = new Bundle();
        bundle1.setName("bundle1");
        bundle1.setDescription("test description");
        bundle1.setResourceName("test");
        bundle2 = new Bundle();
        bundle2.setName("bundle2");
        
        return Arrays.asList(new Object[][] {
                {true, "test1", spanish, newZealand, bundle1, "keyword"},
                {false, "test1", english, newZealand, bundle1, "keyword"},
                {false, "test1", spanish, china, bundle1, "keyword"},
                {false, "test1", spanish, newZealand, bundle2, "keyword"},
                {false, "test1", null, newZealand, bundle1, "keyword"},
                {false, "test1", spanish, null, bundle1, "keyword"},
                {true, "test1", spanish, newZealand, null, "keyword"},
                {false, null, null, null, null, null}
                });
    }
    
    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        preferences = new PreferenceForm();
        
        preferences.addBundle(bundle1);
        preferences.addBundle(bundle2);
        preferences.addCountry(china);
        preferences.addCountry(newZealand);
        preferences.addLanguage(spanish);
        preferences.addLanguage(english);
        
        List<Language> selectedLanguages = 
            Arrays.asList(new Language[] {spanish});
        List<Country> selectedCountries = 
            Arrays.asList(new Country[] {newZealand});
        List<Bundle> selectedBundles = 
            Arrays.asList(new Bundle[] {bundle1});
        
        preferences.setSelectedLanguages(selectedLanguages);
        preferences.setSelectedCountries(selectedCountries);
        preferences.setSelectedBundles(selectedBundles);
    }

    /**
     * Test method for {@link org.tonguetied.web.PreferenceFilter#evaluate(java.lang.Object)}.
     */
    @Test
    public final void testEvaluate() {
        PreferenceFilter filter = new PreferenceFilter(preferences);
        
        boolean actual = filter.evaluate(this.translation);
        
        assertEquals(this.expected, actual);
    }

}
