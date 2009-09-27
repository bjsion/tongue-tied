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

import static org.tonguetied.keywordmanagement.Bundle.TABLE_BUNDLE;
import static org.tonguetied.keywordmanagement.Country.TABLE_COUNTRY;
import static org.tonguetied.keywordmanagement.Keyword.TABLE_KEYWORD;
import static org.tonguetied.keywordmanagement.Language.TABLE_LANGUAGE;
import static org.tonguetied.keywordmanagement.Translation.TABLE_TRANSLATION;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.tonguetied.datatransfer.common.ExportParameters;
import org.tonguetied.datatransfer.common.FormatType;
import org.tonguetied.keywordmanagement.Bundle;
import org.tonguetied.keywordmanagement.Country;
import org.tonguetied.keywordmanagement.Keyword;
import org.tonguetied.keywordmanagement.KeywordService;
import org.tonguetied.keywordmanagement.Language;
import org.tonguetied.keywordmanagement.Translation;
import org.tonguetied.keywordmanagement.Country.CountryCode;
import org.tonguetied.keywordmanagement.Language.LanguageCode;
import org.tonguetied.test.common.AbstractServiceTest;


/**
 * Test class for {@link ResourcePostProcessor}.
 * 
 * @author bsion
 *
 */
public class ResourcePostProcessorTest extends AbstractServiceTest
{
    private Country argentina;
    private Country chile;
    private Country defaultCountry;
    private Country taiwan;

    private Language defaultLanguage;
    private Language spanish;
    private Language chinese;
    
    private Bundle bundle1;
    private Bundle bundle2;
    private Bundle global1;
    private Bundle global2;

    private Keyword keyword1;
    private Keyword keyword2;
    private Keyword keyword3;
//    private static Keyword keyword4;
    
    private Translation translation1_1;
    private Translation translation1_2;
    private Translation translation1_3;
    private Translation translation1_4;
    private Translation translation1_5;
    private Translation translation2_1;
    private Translation translation2_2;
    private Translation translation2_3;
    private Translation translation2_4;
    private Translation translation3_1;
    private Translation translation3_2;
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
        
        bundle1 = new Bundle();
        bundle1.setName("bundle1");
        bundle1.setResourceName("bundle1");
        bundle1.setDescription("this is a test bundle");
        keywordService.saveOrUpdate(bundle1);

        bundle2 = new Bundle();
        bundle2.setName("bundle2");
        bundle2.setResourceName("bundle2");
        bundle2.setDescription("this is a test bundle");
        keywordService.saveOrUpdate(bundle2);
        
        global1 = new Bundle();
        global1.setName("global1");
        global1.setResourceName("global1");
        global1.setGlobal(true);
        global1.setDescription("this is a test bundle");
        keywordService.saveOrUpdate(global1);

        global2 = new Bundle();
        global2.setName("global2");
        global2.setResourceName("global2");
        global2.setGlobal(true);
        global2.setDescription("this is a test bundle");
        keywordService.saveOrUpdate(global2);
        
        keyword1 = new Keyword();
        keyword1.setKeyword("keywordOne");
        keyword1.setContext("keyword 1 context");
        translation1_1 = new Translation();
        translation1_1.setBundle(bundle1);
        translation1_1.setCountry(argentina);
        translation1_1.setLanguage(defaultLanguage);
        translation1_1.setKeyword(keyword1);
        translation1_1.setValue("bundle1, AR, default");
        keyword1.addTranslation(translation1_1);
        translation1_2 = new Translation();
        translation1_2.setBundle(bundle1);
        translation1_2.setCountry(chile);
        translation1_2.setLanguage(defaultLanguage);
        translation1_2.setKeyword(keyword1);
        translation1_2.setValue("bundle1, CH, default");
        keyword1.addTranslation(translation1_2);
        translation1_3 = new Translation();
        translation1_3.setBundle(bundle1);
        translation1_3.setCountry(defaultCountry);
        translation1_3.setLanguage(defaultLanguage);
        translation1_3.setKeyword(keyword1);
        translation1_3.setValue("bundle1, DEFAULT, default");
        keyword1.addTranslation(translation1_3);
        translation1_4 = new Translation();
        translation1_4.setBundle(bundle1);
        translation1_4.setCountry(defaultCountry);
        translation1_4.setLanguage(spanish);
        translation1_4.setKeyword(keyword1);
        translation1_4.setValue("bundle1, DEFAULT, es");
        keyword1.addTranslation(translation1_4);
        translation1_5 = new Translation();
        translation1_5.setBundle(global1);
        translation1_5.setCountry(defaultCountry);
        translation1_5.setLanguage(spanish);
        translation1_5.setKeyword(keyword1);
        translation1_5.setValue("global1, DEFAULT, es");
        keyword1.addTranslation(translation1_5);
        
        keyword2 = new Keyword();
        keyword2.setKeyword("keywordTwo");
        keyword2.setContext("keyword 2 context");
        translation2_1 = new Translation();
        translation2_1.setBundle(bundle1);
        translation2_1.setCountry(defaultCountry);
        translation2_1.setLanguage(chinese);
        translation2_1.setKeyword(keyword2);
        translation2_1.setValue("bundle1, default, zh");
        keyword2.addTranslation(translation2_1);
        translation2_2 = new Translation();
        translation2_2.setBundle(bundle2);
        translation2_2.setCountry(taiwan);
        translation2_2.setLanguage(chinese);
        translation2_2.setKeyword(keyword2);
        translation2_2.setValue("bundle2, TW, zh");
        keyword2.addTranslation(translation2_2);
        translation2_3 = new Translation();
        translation2_3.setBundle(global2);
        translation2_3.setCountry(taiwan);
        translation2_3.setLanguage(chinese);
        translation2_3.setKeyword(keyword2);
        translation2_3.setValue("global2, TW, zh");
        keyword2.addTranslation(translation2_3);
        translation2_4 = new Translation();
        translation2_4.setBundle(global2);
        translation2_4.setCountry(defaultCountry);
        translation2_4.setLanguage(defaultLanguage);
        translation2_4.setKeyword(keyword2);
        translation2_4.setValue("global2, default, default");
        keyword2.addTranslation(translation2_4);
        
        keyword3 = new Keyword();
        keyword3.setKeyword("keywordThree");
        keyword3.setContext("keyword 3 context");
        translation3_1 = new Translation();
        translation3_1.setBundle(global1);
        translation3_1.setCountry(defaultCountry);
        translation3_1.setLanguage(defaultLanguage);
        translation3_1.setKeyword(keyword3);
        translation3_1.setValue("global1, default, default");
        keyword3.addTranslation(translation3_1);
        translation3_2 = new Translation();
        translation3_2.setBundle(global2);
        translation3_2.setCountry(defaultCountry);
        translation3_2.setLanguage(defaultLanguage);
        translation3_2.setKeyword(keyword3);
        translation3_2.setValue("global2, default, default");
        keyword3.addTranslation(translation3_2);
    }
    
    /**
     * Test method for {@link LanguageCentricProcessor#transformData(List)}
     */
    @Test
    public final void testTransformDataWithNullList()
    {
        ExportParameters parameters = new ExportParameters();
        ResourcePostProcessor processor = new ResourcePostProcessor(parameters);
        List<Translation> actual = processor.transformData(null);
        assertTrue(actual.isEmpty());
    }

    /**
     * Test method for {@link LanguageCentricProcessor#transformData(List)}.
     */
    @Test
    public final void testTransformDataWithEmptyList()
    {
        ExportParameters parameters = new ExportParameters();
        ResourcePostProcessor processor = new ResourcePostProcessor(parameters);
        List<Translation> actual = processor.transformData(new ArrayList<Translation>());
        assertTrue(actual.isEmpty());
    }
    
    /**
     * Test method for {@link LanguageCentricProcessor#transformData(List)}.
     */
    @Test
    public final void testTransformData()
    {
        ExportParameters parameters = new ExportParameters();
        List<Translation> input = new ArrayList<Translation>();
        input.add(translation1_1);
        input.add(translation1_2);
        input.add(translation1_3);
        input.add(translation1_4);
        input.add(translation1_5);
        input.add(translation2_1);
        input.add(translation2_2);
        input.add(translation2_3);
        input.add(translation2_4);
        input.add(translation3_1);
        input.add(translation3_2);
        
        parameters.addBundle(bundle1);
        parameters.addBundle(bundle2);
        parameters.addBundle(global1);
        parameters.addBundle(global2);
        parameters.addCountry(argentina);
        parameters.addCountry(chile);
        parameters.addCountry(defaultCountry);
        parameters.addLanguage(chinese);
        parameters.addLanguage(spanish);
        parameters.addLanguage(defaultLanguage);
        parameters.setResultPackaged(false);
        parameters.setFormatType(FormatType.properties);
        parameters.setGlobalsMerged(true);
        
        ResourcePostProcessor processor = new ResourcePostProcessor(parameters);
        List<Translation> actual = processor.transformData(input);
        
        Translation translation;
        List<Translation> expected = new ArrayList<Translation>();
        expected.add(translation1_1);
        expected.add(translation1_2);
        expected.add(translation1_3);
        expected.add(translation1_4);
        translation = translation1_5.deepClone();
        translation.setBundle(bundle2);
        expected.add(translation);
        expected.add(translation2_1);
        expected.add(translation2_2);
        translation = translation2_3.deepClone();
        translation.setBundle(bundle1);
        expected.add(translation);
        translation = translation2_4.deepClone();
        translation.setBundle(bundle1);
        expected.add(translation);
        translation = translation2_4.deepClone();
        translation.setBundle(bundle2);
        expected.add(translation);
        translation = translation3_1.deepClone();
        translation.setBundle(bundle1);
        expected.add(translation);
        translation = translation3_1.deepClone();
        translation.setBundle(bundle2);
        expected.add(translation);
//        translation = translation3_2.deepClone();
//        translation.setBundle(bundle1);
//        expected.add(translation);
//        translation = translation3_2.deepClone();
//        translation.setBundle(bundle2);
//        expected.add(translation);
        
        assertEquals(expected.size(), actual.size());
        for (int i=0; i < expected.size(); i++)
        {
            assertEquals("failed on index " + i, expected.get(i), actual.get(i));
        }
        assertEquals(expected, actual);
    }
    
    /**
     * Test method for {@link LanguageCentricProcessor#transformData(List)}.
     */
    @Test
    public final void testTransformDataUnmergedGlobalBundles()
    {
        ExportParameters parameters = new ExportParameters();
        parameters.addBundle(bundle1);
        parameters.addBundle(bundle2);
        parameters.addBundle(global1);
        parameters.addBundle(global2);
        parameters.addCountry(argentina);
        parameters.addCountry(chile);
        parameters.addCountry(defaultCountry);
        parameters.addLanguage(chinese);
        parameters.addLanguage(spanish);
        parameters.addLanguage(defaultLanguage);
        parameters.setResultPackaged(false);
        parameters.setFormatType(FormatType.properties);
        parameters.setGlobalsMerged(false);
        
        List<Translation> input = new ArrayList<Translation>();
        input.add(translation1_1);
        input.add(translation1_2);
        input.add(translation1_3);
        input.add(translation1_4);
        input.add(translation1_5);
        input.add(translation2_1);
        input.add(translation2_2);
        input.add(translation2_3);
        input.add(translation3_1);
        input.add(translation3_2);
        
        ResourcePostProcessor processor = new ResourcePostProcessor(parameters);
        List<Translation> actual = processor.transformData(input);
        
        assertEquals(input, actual);
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
