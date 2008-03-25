package org.tonguetied.domain;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.tonguetied.domain.Country.CountryCode;
import org.tonguetied.domain.Language.LanguageCode;
import org.tonguetied.domain.Translation.TranslationState;
import org.tonguetied.web.SearchForm;

/**
 * @author bsion
 *
 */
public class SearchFormTest {
    private Language language;
    private Country country;
    private Bundle bundle;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        this.bundle = new Bundle();
        this.bundle.setName("bundle");
        
        this.language = new Language();
        this.language.setCode(LanguageCode.pt);
        this.language.setName("Portuguese");
        
        this.country = new Country();
        this.country.setCode(CountryCode.BR);
        this.country.setName("Brasil");
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test method for {@link org.tonguetied.web.SearchForm#initialize()}.
     */
    @Test
    public void testInitialize() {
        SearchForm searchForm = new SearchForm();
        searchForm.setKeywordKey("keywordKey");
        searchForm.setIgnoreCase(false);
        searchForm.setBundle(bundle);
        searchForm.setCountry(country);
        searchForm.setLanguage(language);
        searchForm.setTranslationState(TranslationState.QUERIED);
        searchForm.setTranslatedText("translatedText");
        
        searchForm.initialize();
        
        assertNull(searchForm.getKeywordKey());
        assertNull(searchForm.getBundle());
        assertNull(searchForm.getCountry());
        assertNull(searchForm.getLanguage());
        assertNull(searchForm.getTranslatedText());
        assertNull(searchForm.getTranslationState());
        assertTrue(searchForm.getIgnoreCase());
    }

}
