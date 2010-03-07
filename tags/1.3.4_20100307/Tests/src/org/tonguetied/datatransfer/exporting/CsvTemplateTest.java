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

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.tonguetied.keywordmanagement.Bundle;
import org.tonguetied.keywordmanagement.Country;
import org.tonguetied.keywordmanagement.Keyword;
import org.tonguetied.keywordmanagement.Language;
import org.tonguetied.keywordmanagement.Translation;
import org.tonguetied.keywordmanagement.Country.CountryCode;
import org.tonguetied.keywordmanagement.Language.LanguageCode;
import org.tonguetied.keywordmanagement.Translation.TranslationState;

/**
 * Test class for testing the output from the csv template. Used rules for csv
 * format from <a href="http://www.creativyst.com/Doc/Articles/CSV/CSV01.htm">
 * Csv Format Rules</a>
 * 
 * @author bsion
 *
 */
public class CsvTemplateTest extends TemplateTester 
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

    private static final String FILE_EXTENSION = "csv";
    private static final String EXPECTED_LINE_1 = 
        "akeyword,\"keyword 1\",en,English,IT,Italia,testBundle,VERIFIED,\"translation 1_1\"";
    private static final String EXPECTED_LINE_2 = 
        "akeyword,\"keyword 1\",en,English,US,United States,testBundle,VERIFIED,\"leading and trailing spaces\"";
    private static final String EXPECTED_LINE_3_1 =
        "anotherkeyword,\"Keyword, 2\",en,English,IT,Italia,testBundle,VERIFIED,\"unix linebreak ";
    private static final String EXPECTED_LINE_3_2 =
        "another line\"";
    private static final String EXPECTED_LINE_4 =
        "anotherkeyword,\"Keyword, 2\",it,Italian,IT,Italia,testBundle,VERIFIED,\"contains \"\"some\"\" quotes\"";
    private static final String EXPECTED_LINE_5 =
        "KeywordThree,\"\",it,Italian,IT,Italia,testBundle,VERIFIED,\"\"";
        
    /**
     * Create a new instance of PropertiesTemplateTest.
     */
    public CsvTemplateTest()
    {
        super("csv.ftl", FILE_EXTENSION);
    }

    @Override
    public void setUp() throws Exception
    {
        super.setUp();
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
        keyword1.setContext("keyword 1  ");
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
        translation1_2.setValue("  leading and trailing spaces    ");
        translation1_2.setState(TranslationState.VERIFIED);
        keyword1.addTranslation(translation1_2);
        
        keyword2 = new Keyword();
        keyword2.setKeyword("anotherkeyword");
        keyword2.setContext("Keyword, 2");
        translation2_1 = new Translation();
        translation2_1.setBundle(bundle);
        translation2_1.setCountry(italy);
        translation2_1.setLanguage(english);
        translation2_1.setKeyword(keyword2);
        translation2_1.setValue("unix linebreak \nanother line");
        translation2_1.setState(TranslationState.VERIFIED);
        keyword2.addTranslation(translation2_1);
        translation2_2 = new Translation();
        translation2_2.setBundle(bundle);
        translation2_2.setCountry(italy);
        translation2_2.setLanguage(italian);
        translation2_2.setValue("contains \"some\" quotes");
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
        translation3_1.setValue(null);
        
        List<Translation> translations = new ArrayList<Translation>();
        translations.add(translation1_1);
        translations.add(translation1_2);
        translations.add(translation2_1);
        translations.add(translation2_2);
        translations.add(translation3_1);

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
        final Collection<File> files = 
            FileUtils.listFiles(getOutputDir().getAbsoluteFile(), getOutputExtensions(), false);
        assertEquals(1, files.size());
        final List<String> lines = 
            FileUtils.readLines(new File(getOutputDir().getAbsoluteFile(), "csv."+FILE_EXTENSION));
        assertEquals(6, lines.size());
        assertEquals(EXPECTED_LINE_1, lines.get(0));
        assertEquals(EXPECTED_LINE_2, lines.get(1));
        assertEquals(EXPECTED_LINE_3_1, lines.get(2));
        assertEquals(EXPECTED_LINE_3_2, lines.get(3));
        assertEquals(EXPECTED_LINE_4, lines.get(4));
        assertEquals(EXPECTED_LINE_5, lines.get(5));
    }
    
    @Test
    @Ignore
    @Override
    public void testTemplateWithEmptyTranslations()
    {
    }
}
