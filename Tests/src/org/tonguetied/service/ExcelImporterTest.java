package org.tonguetied.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.tonguetied.dao.DaoRepository;
import org.tonguetied.domain.Bundle;
import org.tonguetied.domain.Country;
import org.tonguetied.domain.FormatType;
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
public class ExcelImporterTest extends AbstractServiceTest {

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
    
//    private static final String INPUT_FILE = "/LanguageCentricImportData.xls";
    
    @Override
    protected void onSetUpInTransaction() throws Exception {
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
        translation1_1.setState(TranslationState.QUERIED);
        keyword1.addTranslation(translation1_1);
        translation1_2 = new Translation();
        translation1_2.setBundle(bundle1);
        translation1_2.setCountry(defaultCountry);
        translation1_2.setKeyword(keyword1);
        translation1_2.setLanguage(hebrew);
        translation1_2.setValue("\u05D9\u05D5\u05DD\u0020\u05D5\u0027");
        translation1_2.setState(TranslationState.QUERIED);
        keyword1.addTranslation(translation1_2);
        translation1_3 = new Translation();
        translation1_3.setBundle(bundle1);
        translation1_3.setCountry(defaultCountry);
        translation1_3.setKeyword(keyword1);
        translation1_3.setValue("\u661F\u671F\u4E94");
        translation1_3.setLanguage(simplifiedChinese);
        translation1_3.setState(TranslationState.VERIFIED);
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
        translation2_1.setState(TranslationState.QUERIED);
        keyword2.addTranslation(translation2_1);
        translation2_2 = new Translation();
        translation2_2.setBundle(bundle1);
        translation2_2.setCountry(defaultCountry);
        translation2_2.setKeyword(keyword1);
        translation2_2.setLanguage(hebrew);
        translation2_2.setState(TranslationState.VERIFIED);
        keyword2.addTranslation(translation2_2);
        translation2_3 = new Translation();
        translation2_3.setBundle(bundle1);
        translation2_3.setCountry(defaultCountry);
        translation2_3.setKeyword(keyword1);
        translation2_3.setLanguage(simplifiedChinese);
        translation2_3.setState(TranslationState.QUERIED);
        keyword2.addTranslation(translation2_3);
        translation2_4 = new Translation();
        translation2_4.setBundle(bundle1);
        translation2_4.setCountry(taiwan);
        translation2_4.setKeyword(keyword1);
        translation2_4.setLanguage(simplifiedChinese);
        translation2_4.setState(TranslationState.QUERIED);
        keyword2.addTranslation(translation2_4);
        
        daoRepository.saveOrUpdate(defaultLanguage);
        daoRepository.saveOrUpdate(hebrew);
        daoRepository.saveOrUpdate(simplifiedChinese);
        daoRepository.saveOrUpdate(traditionalChinese);
        daoRepository.saveOrUpdate(bundle1);
        daoRepository.saveOrUpdate(bundle2);
        daoRepository.saveOrUpdate(defaultCountry);
        daoRepository.saveOrUpdate(southAfrica);
        daoRepository.saveOrUpdate(taiwan);
        daoRepository.saveOrUpdate(keyword1);
    }
    
    /**
     * Test method for {@link org.tonguetied.service.ExcelImporter#importData(byte[])}.
     */
    public void testImportData() throws Exception {
        File input = new File(System.getProperty("user.dir") + 
                File.separator + "resources" + File.separator + "data" +
                File.separator + "LanguageCentricImportData.xls");
        
        FileInputStream fis = new FileInputStream(input);
        
//      Get the size of the file
        long length = input.length();
    
        if (length > Integer.MAX_VALUE) {
            // File is too large
        }
    
        // Create the byte array to hold the data
        byte[] bytes = new byte[(int)length];
    
        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
               && (numRead=fis.read(bytes, offset, bytes.length-offset)) >= 0) {
            offset += numRead;
        }
    
        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file "+input.getName());
        }
    
        // Close the input stream and return bytes
        fis.close();
        
        Importer importer = Importer.createInstance(daoRepository, FormatType.xlsLanguage);
        importer.importData(bytes);

        List<Keyword> keywords = daoRepository.getKeywords(0, null);
        assertEquals(8, keywords.size());
        
        Keyword actual = daoRepository.getKeyword(keyword1.getKeyword());
        Object[] actranslations = actual.getTranslations().toArray();
        Object[] extranslations = keyword1.getTranslations().toArray();
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
        
        actual = daoRepository.getKeyword(keyword2.getKeyword());
        Object[] translations = actual.getTranslations().toArray();
        assertEquals("Sat", ((Translation)translations[0]).getValue());
        assertEquals("\u05D9\u05D5\u05DD\u0020\u05E9\u0027", ((Translation)translations[1]).getValue());
        assertEquals("\u661F\u671F\u516D", ((Translation)translations[2]).getValue());
        assertEquals("\u661F\u671F\u516D", ((Translation)translations[3]).getValue());
    }

    
    public void setDaoRepository(DaoRepository daoRepository) {
        this.daoRepository = daoRepository;
    }
}
