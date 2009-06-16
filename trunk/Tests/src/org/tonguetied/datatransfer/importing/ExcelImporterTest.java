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
package org.tonguetied.datatransfer.importing;

import static org.tonguetied.datatransfer.importing.Constants.TEST_DATA_DIR;
import static org.tonguetied.keywordmanagement.Bundle.TABLE_BUNDLE;
import static org.tonguetied.keywordmanagement.Country.TABLE_COUNTRY;
import static org.tonguetied.keywordmanagement.Keyword.TABLE_KEYWORD;
import static org.tonguetied.keywordmanagement.Language.TABLE_LANGUAGE;
import static org.tonguetied.keywordmanagement.Translation.TABLE_TRANSLATION;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.tonguetied.datatransfer.common.FormatType;
import org.tonguetied.datatransfer.common.ImportParameters;
import org.tonguetied.datatransfer.dao.TransferRepository;
import org.tonguetied.keywordmanagement.Bundle;
import org.tonguetied.keywordmanagement.Country;
import org.tonguetied.keywordmanagement.Keyword;
import org.tonguetied.keywordmanagement.KeywordService;
import org.tonguetied.keywordmanagement.Language;
import org.tonguetied.keywordmanagement.Translation;
import org.tonguetied.keywordmanagement.Country.CountryCode;
import org.tonguetied.keywordmanagement.Language.LanguageCode;
import org.tonguetied.keywordmanagement.Translation.TranslationState;
import org.tonguetied.test.common.AbstractServiceTest;


/**
 * @author bsion
 *
 */
public class ExcelImporterTest extends AbstractServiceTest
{

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
    
    private KeywordService keywordService;
    private TransferRepository transferRepository;
    
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
        bundle1.setResourceName("bundle1");
        bundle1.setGlobal(false);

        bundle2 = new Bundle();
        bundle2.setName("bundle2");
        bundle2.setResourceName("bundle2");
        bundle2.setGlobal(false);
        
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
        
        keywordService.saveOrUpdate(defaultLanguage);
        keywordService.saveOrUpdate(hebrew);
        keywordService.saveOrUpdate(simplifiedChinese);
        keywordService.saveOrUpdate(traditionalChinese);
        keywordService.saveOrUpdate(bundle1);
        keywordService.saveOrUpdate(bundle2);
        keywordService.saveOrUpdate(defaultCountry);
        keywordService.saveOrUpdate(southAfrica);
        keywordService.saveOrUpdate(taiwan);
        keywordService.saveOrUpdate(keyword1);
    }
    
    /**
     * Test method for {@link org.tonguetied.datatransfer.importing.ExcelImporter#importData(ImportParameters)}.
     */
    public final void testImportData() throws Exception {
        File input = new File(TEST_DATA_DIR, "LanguageCentricImportData.xls");
        byte[] bytes = FileUtils.readFileToByteArray(input);
        
        Importer importer = ImporterFactory.getImporter(
                FormatType.xlsLanguage, keywordService, transferRepository);
        TranslationState expectedState = TranslationState.VERIFIED;
        ImportParameters parameters = new ImportParameters();
        parameters.setData(bytes);
        parameters.setTranslationState(expectedState);
        importer.importData(parameters);

        List<Keyword> keywords = keywordService.getKeywords();
        assertEquals(8, keywords.size());
        
        Keyword actual = keywordService.getKeyword(keyword1.getKeyword());
        Object[] actranslations = actual.getTranslations().toArray();
        Object[] extranslations = keyword1.getTranslations().toArray();
        Translation expectedTrans; 
        Translation actualTrans; 
        for (int i = 0; i < actranslations.length; i++) {
            expectedTrans = (Translation)extranslations[i]; 
            actualTrans = (Translation)actranslations[i]; 
            assertEquals(expectedTrans.getValue(), actualTrans.getValue());
            assertEquals(expectedTrans.getBundle(), actualTrans.getBundle());
            assertEquals(expectedTrans.getCountry(), actualTrans.getCountry());
            assertEquals(expectedTrans.getLanguage(), actualTrans.getLanguage());
            assertNotNull(actualTrans.getState());
            assertEquals(expectedTrans.getState(), actualTrans.getState());
        }
        
        actual = keywordService.getKeyword(keyword2.getKeyword());
        Object[] translations = actual.getTranslations().toArray();
        assertEquals("Sat", ((Translation)translations[0]).getValue());
        assertEquals("\u05D9\u05D5\u05DD\u0020\u05E9\u0027", ((Translation)translations[1]).getValue());
        assertEquals("\u661F\u671F\u516D", ((Translation)translations[2]).getValue());
        assertEquals("\u661F\u671F\u516D", ((Translation)translations[3]).getValue());
    }
    
    @Override
    protected String[] getTableNames()
    {
        return new String[] {TABLE_TRANSLATION, TABLE_KEYWORD, TABLE_BUNDLE, 
                TABLE_COUNTRY, TABLE_LANGUAGE};
    }

    public void setKeywordService(KeywordService keywordService)
    {
        this.keywordService = keywordService;
    }

    public void setTransferRepository(TransferRepository transferRepository)
    {
        this.transferRepository = transferRepository;
    }
}
