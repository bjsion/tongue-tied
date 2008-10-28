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
package org.tonguetied.keywordmanagement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;
import org.tonguetied.keywordmanagement.Bundle;
import org.tonguetied.keywordmanagement.Country;
import org.tonguetied.keywordmanagement.Keyword;
import org.tonguetied.keywordmanagement.Language;
import org.tonguetied.keywordmanagement.Translation;
import org.tonguetied.keywordmanagement.Country.CountryCode;
import org.tonguetied.keywordmanagement.Language.LanguageCode;
import org.tonguetied.keywordmanagement.Translation.TranslationState;
import org.tonguetied.test.common.PersistenceTestBase;



/**
 * @author bsion
 *
 */
public class TranslationPersistenceTest extends PersistenceTestBase {

    @Test
    public final void simplePersistence() {
        Session session;
        Transaction tx;
        
        session = getSession();
        tx = session.beginTransaction();
        assertTrue(tx.isActive());

        Translation translation = new Translation();
        translation.setValue("translated value");
        translation.setState(TranslationState.VERIFIED);
        session.saveOrUpdate(translation);
        session.close();
        
        session = getSession();
        tx = session.beginTransaction();
        Translation reloaded = 
            (Translation) session.get(Translation.class, translation.getId());
        assertEquals(translation, reloaded);
        tx.rollback();
        session.close();
    }
    
    @Test
    public final void persistTranslationWithBundle() {
        Session session;
        Transaction tx;
        
        session = getSession();
        tx = session.beginTransaction();
        assertTrue(tx.isActive());

        // init data
        Bundle bundle = new Bundle();
        bundle.setName("tonguetied Server");
        bundle.setDescription("tonguetied resources");
        bundle.setResourceName("tonguetied");
        session.saveOrUpdate(bundle);
        
        Translation translation = new Translation();
        translation.setState(TranslationState.VERIFIED);
        translation.setValue("translated value");
        translation.setBundle(bundle);
        
        session.saveOrUpdate(translation);
        session.close();
        
        session = getSession();
        tx = session.beginTransaction();
        Translation reloaded = 
            (Translation) session.get(Translation.class, translation.getId());
        assertEquals(translation, reloaded);
        tx.rollback();
        session.close();
    }
    
    @Test
    public final void persistTranslationWithCountry() {
        Session session;
        Transaction tx;
        
        session = getSession();
        tx = session.beginTransaction();
        assertTrue(tx.isActive());

        // init data
        Country country = new Country();
        country.setCode(CountryCode.SG);
        country.setName("Singapore");
        session.saveOrUpdate(country);
        
        Translation translation = new Translation();
        translation.setState(TranslationState.VERIFIED);
        translation.setValue("translated value");
        
        translation.setCountry(country);
        session.saveOrUpdate(translation);
        session.close();
        
        session = getSession();
        tx = session.beginTransaction();
        Translation reloaded = 
            (Translation) session.get(Translation.class, translation.getId());
        assertEquals(translation, reloaded);
        tx.rollback();
        session.close();
    }
    
    @Test
    public final void persistTranslationWithLanguage() {
        Session session;
        Transaction tx;
        
        session = getSession();
        tx = session.beginTransaction();
        assertTrue(tx.isActive());

        // init data
        Language language = new Language();
        language.setCode(LanguageCode.en);
        language.setName("English");
        session.saveOrUpdate(language);

        Translation translation = new Translation();
        translation.setValue("translated value");
        translation.setState(TranslationState.VERIFIED);
        
        translation.setLanguage(language);
        session.saveOrUpdate(translation);
        session.close();
        
        session = getSession();
        tx = session.beginTransaction();
        Translation reloaded = 
            (Translation) session.get(Translation.class, translation.getId());
        assertEquals(translation, reloaded);
        tx.rollback();
        session.close();
    }
    
    @Test
    public final void persistTranslationWithKeyword() {
        Session session;
        Transaction tx;
        
        session = getSession();
        tx = session.beginTransaction();
        assertTrue(tx.isActive());

        // init data
        Keyword keyword = new Keyword();
        keyword.setKeyword("keyword");
        keyword.setContext("context");
        session.saveOrUpdate(keyword);

        Translation translation = new Translation();
        translation.setValue("translated value");
        translation.setState(TranslationState.VERIFIED);

        translation.setKeyword(keyword);
        session.saveOrUpdate(translation);
        session.close();
        
        session = getSession();
        tx = session.beginTransaction();
        Translation reloaded = 
            (Translation) session.get(Translation.class, translation.getId());
        assertEquals(translation.getBundle(), reloaded.getBundle());
        assertEquals(translation.getCountry(), reloaded.getCountry());
        assertEquals(translation.getLanguage(), reloaded.getLanguage());
        assertEquals(translation.getValue(), reloaded.getValue());
        assertNull(reloaded.getKeyword());
        tx.rollback();
        session.close();
    }
}
