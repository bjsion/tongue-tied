package org.tonguetied.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;
import org.tonguetied.dao.DaoRepository;
import org.tonguetied.dao.DaoRepositoryStub;
import org.tonguetied.domain.Bundle;
import org.tonguetied.domain.Country;
import org.tonguetied.domain.ExportParameters;
import org.tonguetied.domain.Keyword;
import org.tonguetied.domain.KeywordByLanguage;
import org.tonguetied.domain.Language;
import org.tonguetied.domain.Translation;
import org.tonguetied.domain.Country.CountryCode;
import org.tonguetied.domain.Language.LanguageCode;


/**
 * @author bsion
 *
 */
public class LanguageCentricProcessorTest {
    private static Country argentina;
    private static Country chile;
    private static Country defaultCountry;
    private static Country taiwan;

    private static Language defaultLanguage;
    private static Language spanish;
    private static Language chinese;
    
    private static Bundle bundle;

    private static Keyword keyword1;
    private static Keyword keyword2;
//    private static Keyword keyword3;
//    private static Keyword keyword4;
    
    private static Translation translation1_1;
    private static Translation translation1_2;
    private static Translation translation1_3;
    private static Translation translation1_4;
    private static Translation translation2_1;
    private static Translation translation2_2;
//    private static Translation translation3_1;
//    private static Translation translation3_2;
//    private static Translation translation3_3;
    
    private static DaoRepository daoRepository;
    
    @BeforeClass
    public static void initialize() {
        argentina = new Country();
        argentina.setCode(CountryCode.AR);
        argentina.setName("Argentina");
        
        chile = new Country();
        chile.setCode(CountryCode.CH);
        chile.setName("Chile");
        
        defaultCountry = new Country();
        defaultCountry.setCode(CountryCode.DEFAULT);
        defaultCountry.setName("Default");
        
        taiwan = new Country();
        taiwan.setCode(CountryCode.TW);
        taiwan.setName("Taiwan");
        
        defaultLanguage = new Language();
        defaultLanguage.setCode(LanguageCode.DEFAULT);
        defaultLanguage.setName("Default");
        
        chinese = new Language();
        chinese.setCode(LanguageCode.zh);
        chinese.setName("Chinese");
        
        spanish = new Language();
        spanish.setCode(LanguageCode.es);
        spanish.setName("Spanish");
        
        bundle = new Bundle();
        bundle.setName("testBundle");
        bundle.setResourceName("test");
        bundle.setDescription("this is a test bundle");
        
        keyword1 = new Keyword();
        keyword1.setKeyword("keywordOne");
        keyword1.setContext("keyword 1 context");
        translation1_1 = new Translation();
        translation1_1.setBundle(bundle);
        translation1_1.setCountry(argentina);
        translation1_1.setLanguage(defaultLanguage);
        translation1_1.setKeyword(keyword1);
        translation1_1.setValue("testBundle, AR, default");
        keyword1.addTranslation(translation1_1);
        translation1_2 = new Translation();
        translation1_2.setBundle(bundle);
        translation1_2.setCountry(chile);
        translation1_2.setLanguage(defaultLanguage);
        translation1_2.setKeyword(keyword1);
        translation1_2.setValue("testBundle, CH, default");
        keyword1.addTranslation(translation1_2);
        translation1_3 = new Translation();
        translation1_3.setBundle(bundle);
        translation1_3.setCountry(defaultCountry);
        translation1_3.setLanguage(defaultLanguage);
        translation1_3.setKeyword(keyword1);
        translation1_3.setValue("testBundle, DEFAULT, default");
        keyword1.addTranslation(translation1_3);
        translation1_4 = new Translation();
        translation1_4.setBundle(bundle);
        translation1_4.setCountry(defaultCountry);
        translation1_4.setLanguage(spanish);
        translation1_4.setKeyword(keyword1);
        translation1_4.setValue("testBundle, DEFAULT, es");
        keyword1.addTranslation(translation1_4);
        
        keyword2 = new Keyword();
        keyword2.setKeyword("keywordTwo");
        keyword2.setContext("keyword 2 context");
        translation2_1 = new Translation();
        translation2_1.setBundle(bundle);
        translation2_1.setCountry(defaultCountry);
        translation2_1.setLanguage(chinese);
        translation2_1.setKeyword(keyword2);
        translation2_1.setValue("testBundle, default, zh");
        keyword2.addTranslation(translation2_1);
        translation2_2 = new Translation();
        translation2_2.setBundle(bundle);
        translation2_2.setCountry(taiwan);
        translation2_2.setLanguage(chinese);
        translation2_2.setKeyword(keyword2);
        translation2_2.setValue("testBundle, TW, zh");
        keyword2.addTranslation(translation2_2);
        
        daoRepository = new DaoRepositoryStub();
        daoRepository.saveOrUpdate(defaultCountry);
    }
    
    /**
     * Test method for {@link org.tonguetied.service.LanguageCentricProcessor#transformData(java.util.List, DaoRepository)}.
     */
    @Test
    public final void testTransformDataWithNullList() {
        LanguageCentricProcessor processor = new LanguageCentricProcessor();
        List<KeywordByLanguage> actual = processor.transformData(null, daoRepository);
        assertTrue(actual.isEmpty());
    }

    /**
     * Test method for {@link org.tonguetied.service.LanguageCentricProcessor#transformData(java.util.List, DaoRepository)}.
     */
    @Test(expected=NullPointerException.class)
    public final void testTransformDataWithNullRepository() {
        LanguageCentricProcessor processor = new LanguageCentricProcessor();
        List<Translation> translations = new ArrayList<Translation>();
        translations.add(translation1_1);
        translations.add(translation1_2);
        translations.add(translation1_3);
        translations.add(translation1_4);
        translations.add(translation2_1);
        translations.add(translation2_2);

        List<KeywordByLanguage> actual = processor.transformData(translations, null);
        assertTrue(actual.isEmpty());
    }

    /**
     * Test method for {@link org.tonguetied.service.LanguageCentricProcessor#transformData(java.util.List, DaoRepository)}.
     */
    @Test
    public final void testTransformDataWithEmptyList() {
        LanguageCentricProcessor processor = new LanguageCentricProcessor();
        List<KeywordByLanguage> actual = processor.transformData(new ArrayList<Translation>(), daoRepository);
        assertTrue(actual.isEmpty());
    }
    
    /**
     * Test method for {@link org.tonguetied.service.LanguageCentricProcessor#transformData(java.util.List, DaoRepository)}.
     */
    @Test
    public final void testTransformData() {
        List<KeywordByLanguage> expected = new ArrayList<KeywordByLanguage>();
        KeywordByLanguage item =new KeywordByLanguage(keyword1.getKeyword(), 
                    keyword1.getContext(), 
                    bundle, 
                    argentina);
        item.addTranslation(translation1_1.getLanguage().getCode(), translation1_1.getValue());
        expected.add(item);
        item = new KeywordByLanguage(keyword1.getKeyword(),
                keyword1.getContext(), 
                bundle,
                chile);
        item.addTranslation(translation1_2.getLanguage().getCode(), translation1_2.getValue());
        expected.add(item);
        item = new KeywordByLanguage(keyword1.getKeyword(), 
                keyword1.getContext(), 
                bundle, 
                defaultCountry);
        item.addTranslation(translation1_3.getLanguage().getCode(), translation1_3.getValue());
        item.addTranslation(translation1_4.getLanguage().getCode(), translation1_4.getValue());
        expected.add(item);
        
        item = new KeywordByLanguage(keyword2.getKeyword(), 
                keyword2.getContext(), 
                bundle, 
                defaultCountry);
        item.addTranslation(translation2_1.getLanguage().getCode(), translation2_1.getValue());
        item.addTranslation(LanguageCode.zht, translation2_2.getValue());
        expected.add(item);
        
        LanguageCentricProcessor processor = new LanguageCentricProcessor();
        List<Translation> translations = new ArrayList<Translation>();
        translations.add(translation1_1);
        translations.add(translation1_2);
        translations.add(translation1_3);
        translations.add(translation1_4);
        translations.add(translation2_1);
        translations.add(translation2_2);

        List<KeywordByLanguage> actual = processor.transformData(translations, daoRepository);
        assertEquals(expected.size(), actual.size());
//        for (int i = 0; i < actual.size(); i++) {
//            assertEquals(expected.getC, actual.get(i));
//        }
        assertEquals(expected, actual);
    }
    
    @Test
    public final void testAddData() {
        Map<String, Object> root = new HashMap<String, Object>();
        ExportParameters parameters = new ExportParameters();
        parameters.addLanguage(defaultLanguage);
        parameters.addLanguage(chinese);
        LanguageCentricProcessor processor = new LanguageCentricProcessor();
        processor.addData(root, parameters);
        
        List<Language> languages = (List<Language>) root.get("languages");
        assertEquals(2, languages.size());
        assertTrue(languages.contains(defaultLanguage));
        assertTrue(languages.contains(chinese));
    }
    
    @Test
    public final void testAddDataWithTraditionalChinese() {
        Map<String, Object> root = new HashMap<String, Object>();
        ExportParameters parameters = new ExportParameters();
        parameters.addLanguage(defaultLanguage);
        parameters.addLanguage(chinese);
        parameters.addCountry(taiwan);
        LanguageCentricProcessor processor = new LanguageCentricProcessor();
        processor.addData(root, parameters);
        
        List<Language> languages = (List<Language>) root.get("languages");
        assertEquals(3, languages.size());
        assertTrue(languages.contains(defaultLanguage));
        assertTrue(languages.contains(chinese));
        Language traditionalChinese = new Language();
        traditionalChinese.setCode(LanguageCode.zht);
        traditionalChinese.setName("Traditional Chinese");
        assertTrue(languages.contains(traditionalChinese));
    }
}
