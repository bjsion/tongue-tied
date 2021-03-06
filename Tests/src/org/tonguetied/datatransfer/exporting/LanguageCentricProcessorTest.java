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
package org.tonguetied.datatransfer.exporting;

import static org.tonguetied.datatransfer.exporting.LanguageCentricProcessor.KEY_LANGUAGES;
import static org.tonguetied.keywordmanagement.Bundle.TABLE_BUNDLE;
import static org.tonguetied.keywordmanagement.Country.TABLE_COUNTRY;
import static org.tonguetied.keywordmanagement.Keyword.TABLE_KEYWORD;
import static org.tonguetied.keywordmanagement.Language.TABLE_LANGUAGE;
import static org.tonguetied.keywordmanagement.Translation.TABLE_TRANSLATION;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.tonguetied.datatransfer.common.ExportParameters;
import org.tonguetied.keywordmanagement.Bundle;
import org.tonguetied.keywordmanagement.Country;
import org.tonguetied.keywordmanagement.Keyword;
import org.tonguetied.keywordmanagement.KeywordByLanguage;
import org.tonguetied.keywordmanagement.KeywordService;
import org.tonguetied.keywordmanagement.Language;
import org.tonguetied.keywordmanagement.Translation;
import org.tonguetied.keywordmanagement.Country.CountryCode;
import org.tonguetied.keywordmanagement.Language.LanguageCode;
import org.tonguetied.test.common.AbstractServiceTest;


/**
 * Test class for the {@link LanguageCentricProcessor} class.
 * 
 * @author bsion
 *
 */
public class LanguageCentricProcessorTest extends AbstractServiceTest
{
    private Country argentina;
    private Country chile;
    private Country defaultCountry;
    private Country taiwan;

    private Language defaultLanguage;
    private Language spanish;
    private Language chinese;
    
    private Bundle bundle;

    private Keyword keyword1;
    private Keyword keyword2;
//    private static Keyword keyword3;
//    private static Keyword keyword4;
    
    private Translation translation1_1;
    private Translation translation1_2;
    private Translation translation1_3;
    private Translation translation1_4;
    private Translation translation2_1;
    private Translation translation2_2;
//    private static Translation translation3_1;
//    private static Translation translation3_2;
//    private static Translation translation3_3;
    
    private KeywordService keywordService;
    
    @Override
    protected void onSetUpInTransaction() throws Exception
    {
        argentina = new Country();
        argentina.setCode(CountryCode.AR);
        argentina.setName("Argentina");
        keywordService.saveOrUpdate(argentina);
                
        chile = new Country();
        chile.setCode(CountryCode.CH);
        chile.setName("Chile");
        keywordService.saveOrUpdate(chile);
        
        defaultCountry = new Country();
        defaultCountry.setCode(CountryCode.DEFAULT);
        defaultCountry.setName("Default");
        keywordService.saveOrUpdate(defaultCountry);
        
        taiwan = new Country();
        taiwan.setCode(CountryCode.TW);
        taiwan.setName("Taiwan");
        keywordService.saveOrUpdate(taiwan);
        
        defaultLanguage = new Language();
        defaultLanguage.setCode(LanguageCode.DEFAULT);
        defaultLanguage.setName("Default");
        keywordService.saveOrUpdate(defaultLanguage);
        
        chinese = new Language();
        chinese.setCode(LanguageCode.zh);
        chinese.setName("Chinese");
        keywordService.saveOrUpdate(chinese);
        
        spanish = new Language();
        spanish.setCode(LanguageCode.es);
        spanish.setName("Spanish");
        keywordService.saveOrUpdate(spanish);
        
        bundle = new Bundle();
        bundle.setName("testBundle");
        bundle.setResourceName("test");
        bundle.setDescription("this is a test bundle");
        keywordService.saveOrUpdate(bundle);
        
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
        
    }
    
    /**
     * Test method for {@link LanguageCentricProcessor#transformData(List)}
     */
    @Test
    public final void testTransformDataWithNullList()
    {
        ExportParameters parameters = new ExportParameters();
        LanguageCentricProcessor processor = 
            new LanguageCentricProcessor(parameters, defaultCountry);
        List<KeywordByLanguage> actual = processor.transformData(null);
        assertTrue(actual.isEmpty());
    }

    /**
     * Test method for {@link LanguageCentricProcessor#transformData(List)}.
     */
    public final void testTransformDataWithNullDefaultCountry()
    {
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
        expected.add(item);
        
        item = new KeywordByLanguage(keyword2.getKeyword(), 
                keyword2.getContext(), 
                bundle, 
                null);
        item.addTranslation(LanguageCode.zht, translation2_2.getValue());
        expected.add(item);
        
        ExportParameters parameters = new ExportParameters();
        LanguageCentricProcessor processor = 
            new LanguageCentricProcessor(parameters, null);
        List<Translation> translations = new ArrayList<Translation>();
        translations.add(translation1_1);
        translations.add(translation1_2);
        translations.add(translation1_3);
        translations.add(translation1_4);
        translations.add(translation2_1);
        translations.add(translation2_2);

        List<KeywordByLanguage> actual = processor.transformData(translations);
        assertEquals(expected.size(), actual.size());
        assertEquals(expected, actual);
    }

    /**
     * Test method for {@link LanguageCentricProcessor#transformData(List)}.
     */
    @Test
    public final void testTransformDataWithEmptyList()
    {
        ExportParameters parameters = new ExportParameters();
        LanguageCentricProcessor processor = 
            new LanguageCentricProcessor(parameters, defaultCountry);
        List<KeywordByLanguage> actual = processor.transformData(new ArrayList<Translation>());
        assertTrue(actual.isEmpty());
    }
    
    /**
     * Test method for {@link LanguageCentricProcessor#transformData(List)}.
     */
    @Test
    public final void testTransformData()
    {
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
        
        ExportParameters parameters = new ExportParameters();
        LanguageCentricProcessor processor = 
            new LanguageCentricProcessor(parameters, defaultCountry);
        List<Translation> translations = new ArrayList<Translation>();
        translations.add(translation1_1);
        translations.add(translation1_2);
        translations.add(translation1_3);
        translations.add(translation1_4);
        translations.add(translation2_1);
        translations.add(translation2_2);

        List<KeywordByLanguage> actual = processor.transformData(translations);
//        assertEquals(expected.size(), actual.size());
//        for (int i = 0; i < actual.size(); i++) {
//            assertEquals(expected.getC, actual.get(i));
//        }
        assertEquals(expected, actual);
    }
    
    @Test
    public final void testAddItem()
    {
        ExportParameters parameters = new ExportParameters();
        LanguageCentricProcessor processor = 
            new LanguageCentricProcessor(parameters, defaultCountry);
        Map<String, Object> root = new HashMap<String, Object>();
        processor.addItems(root);
        assertTrue(root.containsKey(KEY_LANGUAGES));
    }
    
    public void setKeywordService(KeywordService keywordService)
    {
        this.keywordService = keywordService;
    }

    @Override
    protected String[] getTableNames()
    {
        return new String[] {TABLE_TRANSLATION, TABLE_KEYWORD, TABLE_BUNDLE, 
                TABLE_COUNTRY, TABLE_LANGUAGE};
    }
}
