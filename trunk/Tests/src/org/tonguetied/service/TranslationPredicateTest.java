package org.tonguetied.service;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

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
import org.tonguetied.domain.Translation.TranslationState;
import org.tonguetied.service.ExcelImporter.TranslationPredicate;


/**
 * @author bsion
 *
 */
@RunWith(value=Parameterized.class)
public class TranslationPredicateTest {
    private Translation translation;
    private boolean expected;
    private boolean expectedWithNullBundle;
    private boolean expectedWithNullCountry;
    private boolean expectedWithNullLanguage;
    
//    private static TranslationPredicate predicate;
    private static Country mexico;
    private static Language spanish;
    private static Bundle bundle1;

    @Parameters
    public static final Collection<Object[]> data() {
        mexico = new Country();
        mexico.setCode(CountryCode.MX);
        mexico.setName("Mexico");
        
        Country spain = new Country();
        spain.setCode(CountryCode.ES);
        spain.setName("Spain");
        
        spanish = new Language();
        spanish.setCode(LanguageCode.es);
        spanish.setName("Spanish");
        
        Language basque = new Language();
        basque.setCode(LanguageCode.eu);
        basque.setName("Basque");
        
        bundle1 = new Bundle();
        bundle1.setName("bundle1");
        bundle1.setResourceName("bundle1");
        
        Bundle bundle2 = new Bundle();
        bundle2.setName("bundle2");
        bundle2.setResourceName("bundle2");
        
        return Arrays.asList(new Object[][] {
                {spanish, mexico, bundle1, true, false, false, false},
                {null, mexico, bundle1, false, false, false, true},
                {basque, mexico, bundle1, false, false, false, false},
                {spanish, null, bundle1, false, false, true, false},
                {spanish, spain, bundle1, false, false, false, false},
                {spanish, mexico, null, false, true, false, false},
                {spanish, mexico, bundle2, false, false, false, false}
        });
    }

    /**
     * @param language
     * @param country
     * @param bundle
     * @param expected
     * @param expectedWithNullBundle 
     * @param expectedWithNullCountry 
     * @param expectedWithNullLanguage 
     */
    public TranslationPredicateTest(Language language, Country country, Bundle bundle, boolean expected, boolean expectedWithNullBundle, boolean expectedWithNullCountry, boolean expectedWithNullLanguage) {
        this.expected = expected;
        this.expectedWithNullBundle = expectedWithNullBundle;
        this.expectedWithNullCountry = expectedWithNullCountry;
        this.expectedWithNullLanguage = expectedWithNullLanguage;
        this.translation = new Translation();
        this.translation.setCountry(country);
        this.translation.setLanguage(language);
        this.translation.setBundle(bundle);
        this.translation.setState(TranslationState.VERIFIED);
        this.translation.setValue("test");
        Keyword keyword = new Keyword();
        keyword.setKeyword("test");
        keyword.addTranslation(translation);
        this.translation.setKeyword(keyword);
    }

    /**
     * Test method for {@link org.tonguetied.service.ExcelImporter.TranslationPredicate#evaluate(java.lang.Object)}.
     */
    @Test
    public final void testEvaluate() {
        TranslationPredicate predicate =
            new ExcelImporter.TranslationPredicate(bundle1, mexico, spanish);
        boolean actual = predicate.evaluate(translation);
        assertEquals(expected, actual);
    }
    
    @Test
    public final void testEvaluateWithNullBundle() {
        TranslationPredicate predicate = 
            new ExcelImporter.TranslationPredicate(null, mexico, spanish);
        boolean actual = predicate.evaluate(translation);
        assertEquals(expectedWithNullBundle, actual);
    }
    
    @Test
    public final void testEvaluateWithNullCountry() {
        TranslationPredicate predicate = 
            new ExcelImporter.TranslationPredicate(bundle1, null, spanish);
        boolean actual = predicate.evaluate(translation);
        assertEquals(expectedWithNullCountry, actual);
    }
    
    @Test
    public final void testEvaluateWithNullLanguage() {
        TranslationPredicate predicate = 
            new ExcelImporter.TranslationPredicate(bundle1, mexico, null);
        boolean actual = predicate.evaluate(translation);
        assertEquals(expectedWithNullLanguage, actual);
    }
}
