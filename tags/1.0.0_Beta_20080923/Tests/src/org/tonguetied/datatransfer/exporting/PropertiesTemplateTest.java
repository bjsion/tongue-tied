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

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.tonguetied.keywordmanagement.Bundle;
import org.tonguetied.keywordmanagement.Country;
import org.tonguetied.keywordmanagement.Keyword;
import org.tonguetied.keywordmanagement.Language;
import org.tonguetied.keywordmanagement.Translation;
import org.tonguetied.keywordmanagement.Country.CountryCode;
import org.tonguetied.keywordmanagement.Language.LanguageCode;
import org.tonguetied.keywordmanagement.Translation.TranslationState;

/**
 * @author bsion
 *
 */
public class PropertiesTemplateTest extends TemplateTester 
{
    private Country usa;
    private Country italy;

    private Language english;
    private Language italian;
    
    private Bundle bundle;
    
    private Keyword keyword1;
    private Keyword keyword2;
    private Keyword keyword3;
    
    private Translation translation1_1;
    private Translation translation1_2;
    private Translation translation2_1;
    private Translation translation2_2;
    private Translation translation3_1;
    private Translation translation3_2;
    private Translation translation3_3;

    private static final String[] FILE_EXTENSIONS = {"properties"};

    /**
     * Create a new instance of PropertiesTemplateTest.
     */
    public PropertiesTemplateTest() {
        super("properties.ftl", FILE_EXTENSIONS);
    }

    @Override
    public void setUp() {
        usa = new Country();
        usa.setCode(CountryCode.US);
        usa.setName("United States");
        
        italy = new Country();
        italy.setCode(CountryCode.IT);
        italy.setName("Italia");
        
        english = new Language();
        english.setCode(LanguageCode.en);
        english.setName("English");
        
        italian = new Language();
        italian.setCode(LanguageCode.it);
        italian.setName("Italian");
        
        bundle = new Bundle();
        bundle.setName("testBundle");
        bundle.setResourceName("PropertiesTemplateTest");
        bundle.setDescription("this is a test bundle");
        
        keyword1 = new Keyword();
        keyword1.setKeyword("akeyword");
        keyword1.setContext("keyword 1");
        translation1_1 = new Translation();
        translation1_1.setBundle(bundle);
        translation1_1.setCountry(italy);
        translation1_1.setLanguage(english);
        translation1_1.setKeyword(keyword1);
        translation1_1.setValue("translation 1_1");
        translation1_1.setState(TranslationState.VERIFIED);
        keyword1.addTranslation(translation1_1);
        translation1_2 = new Translation();
        translation1_2.setBundle(bundle);
        translation1_2.setCountry(usa);
        translation1_2.setLanguage(english);
        translation1_2.setKeyword(keyword1);
        translation1_2.setValue("split line \\\ncontinue on second");
        translation1_2.setState(TranslationState.VERIFIED);
        keyword1.addTranslation(translation1_2);
        
        keyword2 = new Keyword();
        keyword2.setKeyword("anotherkeyword");
        keyword2.setContext("Keyword 2");
        translation2_1 = new Translation();
        translation2_1.setBundle(bundle);
        translation2_1.setCountry(italy);
        translation2_1.setLanguage(english);
        translation2_1.setKeyword(keyword2);
        translation2_1.setValue("unix linebreak \\\nanother line");
        translation2_1.setState(TranslationState.VERIFIED);
        keyword2.addTranslation(translation2_1);
        translation2_2 = new Translation();
        translation2_2.setBundle(bundle);
        translation2_2.setCountry(italy);
        translation2_2.setLanguage(italian);
        translation2_2.setValue("xml < ! & \" '>");
        translation2_2.setKeyword(keyword2);
        translation2_2.setState(TranslationState.VERIFIED);
        keyword2.addTranslation(translation2_2);
        
        keyword3 = new Keyword();
        keyword3.setKeyword("KeywordThree");
        translation3_1 = new Translation();
        translation3_1.setBundle(bundle);
        translation3_1.setCountry(italy);
        translation3_1.setLanguage(italian);
        translation3_1.setState(TranslationState.VERIFIED);
        translation3_1.setKeyword(keyword3);
        translation3_1.setValue("translation 3_1");
        keyword3.addTranslation(translation3_1);
        translation3_2 = new Translation();
        translation3_2.setBundle(bundle);
        translation3_2.setCountry(usa);
        translation3_2.setLanguage(english);
        translation3_2.setKeyword(keyword3);
        translation3_2.setState(TranslationState.VERIFIED);
        translation3_2.setValue("");
        keyword3.addTranslation(translation3_2);
        translation3_3 = new Translation();
        translation3_3.setBundle(bundle);
        translation3_3.setCountry(italy);
        translation3_3.setLanguage(english);
        translation3_3.setKeyword(keyword3);
        translation3_3.setValue("windows linebreak \\\r\nanother line");
        translation3_3.setState(TranslationState.VERIFIED);
        keyword3.addTranslation(translation3_3);
        
        List<Translation> translations = new ArrayList<Translation>();
        translations.add(translation1_1);
        translations.add(translation1_2);
        translations.add(translation2_1);
        translations.add(translation2_2);
        translations.add(translation3_1);
        translations.add(translation3_2);
        translations.add(translation3_3);

        super.setTranslations(translations);
    }

    @Override
    public void destroy() throws Exception
    {
        super.destroy();
        super.setTranslations(null);
    }

    @Override
    public void runAssertions() throws Exception
    {
        Collection<File> files = FileUtils.listFiles(getOutputDir(), getOutputExtensions(), false);
        assertEquals(3, files.size());
        Properties expected;
        Properties actual;
        byte[] bytes;
        ByteArrayInputStream bais = null;
        try 
        {
            for (File file :files) 
            {
                bytes = FileUtils.readFileToByteArray(file);
                bais = new ByteArrayInputStream(bytes);
                actual = new Properties();
                actual.load(bais);
                if ("PropertiesTemplateTest_en_US.properties".equals(file.getName()))
                {
                    expected = new Properties();
                    expected.put("akeyword", "split line continue on second");
                    // No KeywordThree as the value is the empty string so will be 
                    // omitted
                    assertEquals(expected, actual);
                }
                else if ("PropertiesTemplateTest_en_IT.properties".equals(file.getName()))
                {
                    expected = new Properties();
                    expected.put("akeyword", "translation 1_1");
                    expected.put("anotherkeyword", "unix linebreak another line");
                    expected.put("KeywordThree", "windows linebreak another line");
                    assertEquals(expected, actual);
                }
                else if ("PropertiesTemplateTest_it_IT.properties".equals(file.getName()))
                {
                    expected = new Properties();
                    expected.put("KeywordThree", "translation 3_1");
                    expected.put("anotherkeyword", "xml < ! & \" '>");
                    assertEquals(expected, actual);
                }
                else
                {
                    fail("Unexpected file: " + file.getPath());
                }
            }
        }
        finally
        {
            if (bais != null)
                bais.close();
        }
    }
}
