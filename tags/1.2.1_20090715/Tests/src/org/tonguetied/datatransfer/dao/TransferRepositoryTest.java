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
package org.tonguetied.datatransfer.dao;

import static org.tonguetied.keywordmanagement.Bundle.TABLE_BUNDLE;
import static org.tonguetied.keywordmanagement.Country.TABLE_COUNTRY;
import static org.tonguetied.keywordmanagement.Keyword.TABLE_KEYWORD;
import static org.tonguetied.keywordmanagement.Language.TABLE_LANGUAGE;
import static org.tonguetied.keywordmanagement.Translation.TABLE_TRANSLATION;

import java.util.List;

import org.springframework.test.annotation.ExpectedException;
import org.tonguetied.datatransfer.common.ExportParameters;
import org.tonguetied.keywordmanagement.Bundle;
import org.tonguetied.keywordmanagement.Country;
import org.tonguetied.keywordmanagement.Keyword;
import org.tonguetied.keywordmanagement.Language;
import org.tonguetied.keywordmanagement.Translation;
import org.tonguetied.keywordmanagement.Country.CountryCode;
import org.tonguetied.keywordmanagement.Language.LanguageCode;
import org.tonguetied.keywordmanagement.Translation.TranslationState;
import org.tonguetied.test.common.AbstractServiceTest;


public class TransferRepositoryTest extends AbstractServiceTest {
    
    private Country singapore;
    private Country australia;

    private Language english;
    private Language chinese;
    
    private Bundle bundle;
    
    private Keyword keyword1;
    private Keyword keyword2;
    private Keyword keyword3;
    private Keyword keyword4;
    
    private Translation translation1_1;
    private Translation translation1_2;
    private Translation translation2_1;
    private Translation translation3_1;
    private Translation translation4_1;
    private Translation translation4_2;
    
    private TransferRepository transferRepository;
    
    @Override
    protected void onSetUpInTransaction() throws Exception {
        singapore = new Country();
        singapore.setCode(CountryCode.SG);
        singapore.setName("Singapore");
        
        australia = new Country();
        australia.setCode(CountryCode.AU);
        australia.setName("Australia");
        
        english = new Language();
        english.setCode(LanguageCode.en);
        english.setName("English");
        
        chinese = new Language();
        chinese.setCode(LanguageCode.zh);
        chinese.setName("Simplified Chinese");
        
        bundle = new Bundle();
        bundle.setName("testBundle");
        bundle.setResourceName("test");
        bundle.setDescription("this is a test bundle");
        
        getCountryRepository().saveOrUpdate(singapore);
        getCountryRepository().saveOrUpdate(australia);
        getLanguageRepository().saveOrUpdate(english);
        getLanguageRepository().saveOrUpdate(chinese);
        getBundleRepository().saveOrUpdate(bundle);
        
        keyword1 = new Keyword();
        keyword1.setKeyword("akeyword");
        keyword1.setContext("keyword 1");
        translation1_1 = new Translation();
        translation1_1.setBundle(bundle);
        translation1_1.setCountry(australia);
        translation1_1.setLanguage(english);
        translation1_1.setKeyword(keyword1);
        translation1_1.setValue("a keyword");
        translation1_1.setState(TranslationState.UNVERIFIED);
        keyword1.addTranslation(translation1_1);
        translation1_2 = new Translation();
        translation1_2.setBundle(bundle);
        translation1_2.setCountry(singapore);
        translation1_2.setLanguage(english);
        translation1_2.setKeyword(keyword1);
        translation1_2.setValue("a keyword lah");
        translation1_2.setState(TranslationState.QUERIED);
        keyword1.addTranslation(translation1_2);
        
        keyword2 = new Keyword();
        keyword2.setKeyword("anotherkeyword");
        keyword2.setContext("Keyword 2");
        translation2_1 = new Translation();
        translation2_1.setBundle(bundle);
        translation2_1.setCountry(australia);
        translation2_1.setLanguage(english);
        translation2_1.setKeyword(keyword2);
        translation2_1.setValue("another keyword");
        translation2_1.setState(TranslationState.VERIFIED);
        keyword2.addTranslation(translation2_1);
        
        keyword3 = new Keyword();
        keyword3.setKeyword("differentKeyword");
        keyword3.setContext("keyword 3");
        translation3_1 = new Translation();
        translation3_1.setBundle(bundle);
        translation3_1.setCountry(singapore);
        translation3_1.setLanguage(chinese);
        translation3_1.setKeyword(keyword3);
        translation3_1.setValue("different keyword");
        translation3_1.setState(TranslationState.QUERIED);
        keyword3.addTranslation(translation3_1);
        
        keyword4 = new Keyword();
        keyword4.setKeyword("oneOtherKeyword");
        keyword4.setContext("Keyword 4");
        translation4_1 = new Translation();
        translation4_1.setBundle(bundle);
        translation4_1.setCountry(singapore);
        translation4_1.setLanguage(chinese);
        translation4_1.setKeyword(keyword4);
        translation4_1.setValue("one other keyword");
        translation4_1.setState(TranslationState.QUERIED);
        keyword4.addTranslation(translation4_1);
        translation4_2 = new Translation();
        translation4_2.setKeyword(keyword4);
        translation4_2.setValue("no bundle country language");
        translation4_2.setState(TranslationState.UNVERIFIED);
        keyword4.addTranslation(translation4_2);
        
        getKeywordRepository().saveOrUpdate(keyword1);
        getKeywordRepository().saveOrUpdate(keyword2);
        getKeywordRepository().saveOrUpdate(keyword3);
        getKeywordRepository().saveOrUpdate(keyword4);
    }

    @ExpectedException(IllegalArgumentException.class)
    public final void testFindTranslationsWithNoLanguage() {
        ExportParameters parameters = new ExportParameters();
        parameters.addCountry(singapore);
        parameters.addBundle(bundle);
        parameters.setTranslationState(TranslationState.VERIFIED);
        transferRepository.findTranslations(parameters);
    }

    @ExpectedException(IllegalArgumentException.class)
    public final void testFindTranslationsWithNoCountry() {
        ExportParameters parameters = new ExportParameters();
        parameters.addLanguage(english);
        parameters.addBundle(bundle);
        parameters.setTranslationState(TranslationState.VERIFIED);
        transferRepository.findTranslations(parameters);
    }

    @ExpectedException(IllegalArgumentException.class)
    public final void testFindTranslationsWithNoBundle() {
        ExportParameters parameters = new ExportParameters();
        parameters.addCountry(singapore);
        parameters.addLanguage(english);
        parameters.setTranslationState(TranslationState.VERIFIED);
        transferRepository.findTranslations(parameters);
    }

    @ExpectedException(IllegalArgumentException.class)
    public final void testFindTranslationsWithNoTranslationState() {
        ExportParameters parameters = new ExportParameters();
        parameters.addBundle(bundle);
        parameters.addCountry(singapore);
        parameters.addLanguage(english);
        transferRepository.findTranslations(parameters);
    }

    public final void testFindTranslations() {
        ExportParameters parameters = new ExportParameters();
        parameters.addLanguage(chinese);
        parameters.addLanguage(english);
        parameters.addCountry(singapore);
        parameters.addBundle(bundle);
        parameters.setTranslationState(TranslationState.QUERIED);
        List<Translation> translations = 
            transferRepository.findTranslations(parameters);
        assertEquals(3, translations.size());
        assertEquals(translation1_2, translations.get(0));
        assertEquals(translation3_1, translations.get(1));
        assertEquals(translation4_1, translations.get(2));
//        assertTrue(translations.contains(translation1_2));
//        assertTrue(translations.contains(translation3_1));
//        assertTrue(translations.contains(translation4_1));
    }

    @Override
    protected String[] getTableNames()
    {
        return new String[] {TABLE_TRANSLATION, TABLE_KEYWORD, TABLE_BUNDLE, 
                TABLE_COUNTRY, TABLE_LANGUAGE};
    }

    public void setTransferRepository(TransferRepository transferRepository) {
        this.transferRepository = transferRepository;
    }
}
