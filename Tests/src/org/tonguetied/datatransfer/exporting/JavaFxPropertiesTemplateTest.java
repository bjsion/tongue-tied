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

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
 * Test class for testing the output from the properties template.
 * 
 * @author bsion
 *
 */
public class JavaFxPropertiesTemplateTest extends TemplateTester 
{
    private Country usa;
    private Country india;

    private Language english;
    private Language hindi;
    
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

    private static final String FILE_EXTENSION = "fxproperties";

    /**
     * Create a new instance of PropertiesTemplateTest.
     */
    public JavaFxPropertiesTemplateTest()
    {
        super("javafx.ftl", FILE_EXTENSION);
    }

    @Override
    public void setUp() throws Exception
    {
        super.setUp();
        usa = new Country();
        usa.setCode(CountryCode.US);
        usa.setName("United States");
        
        india = new Country();
        india.setCode(CountryCode.IN);
        india.setName("India");
        
        english = new Language();
        english.setCode(LanguageCode.en);
        english.setName("English");
        
        hindi = new Language();
        hindi.setCode(LanguageCode.hi);
        hindi.setName("Hindi");
        
        bundle = new Bundle();
        bundle.setName("testBundle");
        bundle.setResourceName("PropertiesTemplateTest");
        bundle.setDescription("this is a test bundle");
        
        keyword1 = new Keyword();
        keyword1.setKeyword("akeyword");
        keyword1.setContext("keyword 1");
        translation1_1 = new Translation();
        translation1_1.setBundle(bundle);
        translation1_1.setCountry(india);
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
        keyword2.setKeyword("another keyword");
        keyword2.setContext("Keyword 2");
        translation2_1 = new Translation();
        translation2_1.setBundle(bundle);
        translation2_1.setCountry(india);
        translation2_1.setLanguage(english);
        translation2_1.setKeyword(keyword2);
        translation2_1.setValue("unix linebreak \\\nanother line");
        translation2_1.setState(TranslationState.VERIFIED);
        keyword2.addTranslation(translation2_1);
        translation2_2 = new Translation();
        translation2_2.setBundle(bundle);
        translation2_2.setCountry(india);
        translation2_2.setLanguage(hindi);
        translation2_2.setValue("xml < ! & \" '>");
        translation2_2.setKeyword(keyword2);
        translation2_2.setState(TranslationState.VERIFIED);
        keyword2.addTranslation(translation2_2);
        
        keyword3 = new Keyword();
        keyword3.setKeyword("Keyword Three");
        translation3_1 = new Translation();
        translation3_1.setBundle(bundle);
        translation3_1.setCountry(india);
        translation3_1.setLanguage(hindi);
        translation3_1.setState(TranslationState.VERIFIED);
        translation3_1.setKeyword(keyword3);
        translation3_1.setValue("नमस्ते");
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
        translation3_3.setCountry(india);
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
        final Collection<File> files = FileUtils.listFiles(
                getOutputDir().getAbsoluteFile(), getOutputExtensions(), false);
        assertEquals(3, files.size());
//        byte[] bytes;
//        ByteArrayInputStream bais = null;
//        try 
//        {
            List<String> actual;
            List<String> expected;
            for (File file :files) 
            {
                actual = FileUtils.readLines(file);
//                bytes = FileUtils.readFileToByteArray(file);
//                bais = new ByteArrayInputStream(bytes);
//                actual = new HashMap<String, String>();
//                actual.load(bais);
                if ("PropertiesTemplateTest_en_US.fxproperties".equals(file.getName()))
                {
                    assertEquals(1, actual.size());
                    expected = new ArrayList<String>();
                    expected.add("\"akeyword\" = \"split line \\\\\\ncontinue on second\"");
                    // No KeywordThree as the value is the empty string so will be 
                    // omitted
                    assertEquals(expected, actual);
                }
                else if ("PropertiesTemplateTest_en_IN.fxproperties".equals(file.getName()))
                {
                    expected = new ArrayList<String>();
                    expected.add("\"akeyword\" = \"translation 1_1\"");
                    expected.add("\"another keyword\" = \"unix linebreak \\\\\\nanother line\"");
                    expected.add("\"Keyword Three\" = \"windows linebreak \\\\\\r\\nanother line\"");
                    assertEquals(expected, actual);
                }
                else if ("PropertiesTemplateTest_hi_IN.fxproperties".equals(file.getName()))
                {
                    expected = new ArrayList<String>();
                    expected.add("\"another keyword\" = \"xml < ! & \" '>\"");
                    expected.add("\"Keyword Three\" = \"नमस्ते\"");
                    assertEquals(expected, actual);
                }
                else
                {
                    fail("Unexpected file: " + file.getPath());
                }
            }
//        }
//        finally
//        {
//            if (bais != null)
//                bais.close();
//        }
    }
}
