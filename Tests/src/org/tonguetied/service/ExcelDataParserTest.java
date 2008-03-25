package org.tonguetied.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.eventusermodel.HSSFEventFactory;
import org.apache.poi.hssf.eventusermodel.HSSFRequest;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.junit.Before;
import org.junit.Test;
import org.tonguetied.dao.DaoRepository;
import org.tonguetied.dao.DaoRepositoryStub;
import org.tonguetied.domain.Bundle;
import org.tonguetied.domain.Country;
import org.tonguetied.domain.Keyword;
import org.tonguetied.domain.Language;
import org.tonguetied.domain.Translation;
import org.tonguetied.domain.Country.CountryCode;
import org.tonguetied.domain.Language.LanguageCode;
import org.tonguetied.domain.Translation.TranslationState;


/**
 * @author bsion
 *
 */
public class ExcelDataParserTest {
    
    private Language defaultLanguage;
    private Language hebrew;
    private Language simplifiedChinese;
    private Language traditionalChinese;
    
    private Country defaultCountry;
    private Country southAfrica;
    private Country taiwan;
    
    private Bundle bundle1;
    private Bundle bundle2;
    
    private Keyword keyword1;
    private Translation translation1_1;
    private Translation translation1_2;
    private Translation translation1_3;
    private Translation translation1_4;
    
    private Keyword keyword2;
    private Translation translation2_1;
    private Translation translation2_2;
    private Translation translation2_3;
    private Translation translation2_4;
    
    private DaoRepository daoRepository;
    
    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        defaultLanguage = new Language();
        defaultLanguage.setCode(LanguageCode.DEFAULT);
        defaultLanguage.setName("Default");
        
        hebrew = new Language();
        hebrew.setCode(LanguageCode.he);
        hebrew.setName("Hebrew");
        
        simplifiedChinese = new Language();
        simplifiedChinese.setCode(LanguageCode.zh);
        simplifiedChinese.setName("Simplified Chinese");

        traditionalChinese = new Language();
        traditionalChinese.setCode(LanguageCode.zht);
        traditionalChinese.setName("Traditional Chinese");
        
        defaultCountry = new Country();
        defaultCountry.setCode(CountryCode.DEFAULT);
        defaultCountry.setName("Default");
        
        southAfrica = new Country();
        southAfrica.setCode(CountryCode.SA);
        southAfrica.setName("South Africa");
        
        taiwan = new Country();
        taiwan.setCode(CountryCode.TW);
        taiwan.setName("Taiwan");
        
        bundle1 = new Bundle();
        bundle1.setName("bundle1");

        bundle2 = new Bundle();
        bundle2.setName("bundle2");
        
        keyword1 = new Keyword();
        keyword1.setKeyword("Fri");
        keyword1.setContext("Acronym for Friday");
        translation1_1 = new Translation();
        translation1_1.setBundle(bundle1);
        translation1_1.setCountry(defaultCountry);
        translation1_1.setKeyword(keyword1);
        translation1_1.setValue("Fri");
        translation1_1.setLanguage(defaultLanguage);
        translation1_1.setState(TranslationState.UNVERIFIED);
        keyword1.addTranslation(translation1_1);
        translation1_2 = new Translation();
        translation1_2.setBundle(bundle1);
        translation1_2.setCountry(defaultCountry);
        translation1_2.setKeyword(keyword1);
        translation1_2.setLanguage(hebrew);
        translation1_2.setValue("\u05D9\u05D5\u05DD\u0020\u05D5\u0027");
        translation1_2.setState(TranslationState.UNVERIFIED);
        keyword1.addTranslation(translation1_2);
        translation1_3 = new Translation();
        translation1_3.setBundle(bundle1);
        translation1_3.setCountry(defaultCountry);
        translation1_3.setKeyword(keyword1);
        translation1_3.setValue("\u661F\u671F\u4E94");
        translation1_3.setLanguage(simplifiedChinese);
        translation1_3.setState(TranslationState.UNVERIFIED);
        keyword1.addTranslation(translation1_3);
        translation1_4 = new Translation();
        translation1_4.setBundle(bundle1);
        translation1_4.setCountry(taiwan);
        translation1_4.setKeyword(keyword1);
        translation1_4.setLanguage(simplifiedChinese);
        translation1_4.setValue("\u661F\u671F\u4E94");
        translation1_4.setState(TranslationState.UNVERIFIED);
        keyword1.addTranslation(translation1_4);

        keyword2 = new Keyword();
        keyword2.setKeyword("Sat");
        keyword2.setContext("Acronym for Saturday");
        translation2_1 = new Translation();
        translation2_1.setBundle(bundle1);
        translation2_1.setCountry(defaultCountry);
        translation2_1.setKeyword(keyword1);
        translation2_1.setValue("Sat");
        translation2_1.setLanguage(defaultLanguage);
        translation2_1.setState(TranslationState.UNVERIFIED);
        keyword2.addTranslation(translation2_1);
        translation2_2 = new Translation();
        translation2_2.setBundle(bundle1);
        translation2_2.setCountry(defaultCountry);
        translation2_2.setKeyword(keyword1);
        translation2_2.setLanguage(hebrew);
        translation2_2.setValue("\u05D9\u05D5\u05DD\u0020\u05E9\u0027");
        translation2_2.setState(TranslationState.UNVERIFIED);
        keyword2.addTranslation(translation2_2);
        translation2_3 = new Translation();
        translation2_3.setBundle(bundle1);
        translation2_3.setCountry(defaultCountry);
        translation2_3.setKeyword(keyword1);
        translation2_3.setLanguage(simplifiedChinese);
        translation2_3.setValue("\u661F\u671F\u516D");
        translation2_3.setState(TranslationState.UNVERIFIED);
        keyword2.addTranslation(translation2_3);
        translation2_4 = new Translation();
        translation2_4.setBundle(bundle1);
        translation2_4.setCountry(taiwan);
        translation2_4.setKeyword(keyword1);
        translation2_4.setLanguage(simplifiedChinese);
        translation2_4.setValue("\u661F\u671F\u516D");
        translation2_4.setState(TranslationState.UNVERIFIED);
        keyword2.addTranslation(translation2_4);
        
        daoRepository = new DaoRepositoryStub();
        daoRepository.saveOrUpdate(defaultLanguage);
        daoRepository.saveOrUpdate(hebrew);
        daoRepository.saveOrUpdate(simplifiedChinese);
        daoRepository.saveOrUpdate(traditionalChinese);
        daoRepository.saveOrUpdate(bundle1);
        daoRepository.saveOrUpdate(bundle2);
        daoRepository.saveOrUpdate(defaultCountry);
        daoRepository.saveOrUpdate(southAfrica);
        daoRepository.saveOrUpdate(taiwan);
    }

    /**
     * Test method for {@link org.tonguetied.service.ExcelDataParser#processRecord(org.apache.poi.hssf.record.Record)}.
     */
    @Test
    public final void testProcessRecord() throws Exception {
        ExcelDataParser parser = new ExcelDataParser(daoRepository);
        FileInputStream fis = null;
        InputStream dis = null;
        try {
            // create a new file input stream with the input file specified
            // at the command line
            File input = new File(System.getProperty("user.dir") + 
                    File.separator + "resources" + File.separator + "data" +
                    File.separator + "LanguageCentricImportData.xls");
            
            fis = new FileInputStream(input);
            // create a new org.apache.poi.poifs.filesystem.Filesystem
            POIFSFileSystem poifs = new POIFSFileSystem(fis);
            // get the Workbook (excel part) stream in a InputStream
            InputStream din = poifs.createDocumentInputStream("Workbook");
            // construct out HSSFRequest object
            HSSFRequest req = new HSSFRequest();
            // lazy listen for ALL records with the listener shown above
            req.addListenerForAllRecords(parser);
            // create our event factory
            HSSFEventFactory factory = new HSSFEventFactory();
            // process our events based on the document input stream
            factory.processEvents(req, din);
        } finally {
            // and our document input stream (don't want to leak these!)
            if (dis != null)
                dis.close();
            // once all the events are processed close our file input stream
            if (fis != null)
                fis.close();
        }
        
        List<Language> languages = parser.getLanguages(); 
        assertEquals(4, languages.size());
        assertTrue(languages.contains(defaultLanguage));
        assertTrue(languages.contains(hebrew));
        assertTrue(languages.contains(simplifiedChinese));
        assertTrue(languages.contains(traditionalChinese));
        
        Map<String, Keyword> keywords = parser.getKeywords();
        assertEquals(8, keywords.size());
        Keyword actual = keywords.get(keyword1.getKeyword());
        assessKeyword(keyword1, actual);
        
        actual = keywords.get(keyword2.getKeyword());
        assessKeyword(keyword2, actual);
    }

    /**
     * @param keywords
     */
    private void assessKeyword(Keyword expected, Keyword actual) {
        assertEquals(expected.getKeyword(), actual.getKeyword());
        assertEquals(expected.getContext(), actual.getContext());
        Object[] actranslations = actual.getTranslations().toArray();
        Object[] extranslations = expected.getTranslations().toArray();
        for (int i = 0; i < actranslations.length; i++) {
            Translation expectedTrans = (Translation)extranslations[i]; 
            Translation actualTrans = (Translation)actranslations[i]; 
            assertEquals(expectedTrans.getValue(), actualTrans.getValue());
            assertEquals(expectedTrans.getBundle(), actualTrans.getBundle());
            assertEquals(expectedTrans.getCountry(), actualTrans.getCountry());
            assertEquals(expectedTrans.getLanguage(), actualTrans.getLanguage());
            assertNotNull(actualTrans.getState());
            assertEquals(expectedTrans.getState(), actualTrans.getState());
        }
    }
}