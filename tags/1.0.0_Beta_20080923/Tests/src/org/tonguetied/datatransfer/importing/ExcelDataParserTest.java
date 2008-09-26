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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.eventusermodel.HSSFEventFactory;
import org.apache.poi.hssf.eventusermodel.HSSFRequest;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.junit.Test;
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
public class ExcelDataParserTest extends AbstractServiceTest {
    
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
        translation1_1 = new Translation(bundle1,defaultCountry, defaultLanguage, "Fri", TranslationState.UNVERIFIED);
        keyword1.addTranslation(translation1_1);
        translation1_2 = new Translation(bundle1, defaultCountry, hebrew, 
            "\u05D9\u05D5\u05DD\u0020\u05D5\u0027", TranslationState.UNVERIFIED);
        keyword1.addTranslation(translation1_2);
        translation1_3 = new Translation(bundle1, defaultCountry, simplifiedChinese,  
                "\u661F\u671F\u4E94", TranslationState.UNVERIFIED);
        keyword1.addTranslation(translation1_3);
        translation1_4 = new Translation(bundle1, taiwan, simplifiedChinese,
                "\u661F\u671F\u4E94", TranslationState.UNVERIFIED);
        keyword1.addTranslation(translation1_4);

        keyword2 = new Keyword();
        keyword2.setKeyword("Sat");
        keyword2.setContext("Acronym for Saturday");
        translation2_1 = new Translation(bundle1, defaultCountry, defaultLanguage, 
                "Sat", TranslationState.UNVERIFIED);
        keyword2.addTranslation(translation2_1);
        translation2_2 = new Translation(bundle1, defaultCountry, hebrew,
                "\u05D9\u05D5\u05DD\u0020\u05E9\u0027", TranslationState.UNVERIFIED);
        keyword2.addTranslation(translation2_2);
        translation2_3 = new Translation(bundle1, defaultCountry, simplifiedChinese,
                "\u661F\u671F\u516D", TranslationState.UNVERIFIED);
        keyword2.addTranslation(translation2_3);
        translation2_4 = new Translation(bundle1, taiwan, simplifiedChinese,
                "\u661F\u671F\u516D", TranslationState.UNVERIFIED);
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
    }

    /**
     * Test method for {@link org.tonguetied.datatransfer.importing.ExcelDataParser#processRecord(org.apache.poi.hssf.record.Record)}.
     */
    @Test
    public final void testProcessRecord() throws Exception {
        ExcelDataParser parser = new ExcelDataParser(keywordService);
        InputStream is = null;
        try {
            // create a new file input stream with the input file specified
            // at the command line
            File input = new File(TEST_DATA_DIR, "LanguageCentricImportData.xls");
            
            is = new BufferedInputStream(new FileInputStream(input));
            // create a new org.apache.poi.poifs.filesystem.Filesystem
            POIFSFileSystem poifs = new POIFSFileSystem(is);
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
            // once all the events are processed close our file input stream
            if (is != null)
                is.close();
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

    public void setKeywordService(final KeywordService keywordService) {
        this.keywordService = keywordService;
    }
}
