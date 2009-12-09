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
package org.tonguetied.audit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.tonguetied.audit.AuditLogRecord.TABLE_AUDIT_LOG_RECORD;
import static org.tonguetied.keywordmanagement.Bundle.TABLE_BUNDLE;
import static org.tonguetied.keywordmanagement.Country.TABLE_COUNTRY;
import static org.tonguetied.keywordmanagement.Keyword.TABLE_KEYWORD;
import static org.tonguetied.keywordmanagement.Language.TABLE_LANGUAGE;
import static org.tonguetied.keywordmanagement.Translation.TABLE_TRANSLATION;

import java.util.List;
import java.util.Properties;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.tonguetied.audit.AuditLogRecord.Operation;
import org.tonguetied.keywordmanagement.Bundle;
import org.tonguetied.keywordmanagement.Country;
import org.tonguetied.keywordmanagement.Keyword;
import org.tonguetied.keywordmanagement.Language;
import org.tonguetied.keywordmanagement.Translation;
import org.tonguetied.keywordmanagement.Country.CountryCode;
import org.tonguetied.keywordmanagement.Language.LanguageCode;
import org.tonguetied.keywordmanagement.Translation.TranslationState;
import org.tonguetied.test.common.PersistenceTestBase;
import org.tonguetied.usermanagement.User;
import org.tonguetied.utils.database.EmbeddedDatabaseServer;

/**
 * Test the event listeners used to log events when persisting data.
 * 
 * @author bsion
 *
 */
public class AuditLogEventListenerTest extends PersistenceTestBase
{
    private Country country;
    private Bundle bundle;
    private Bundle bundle2;
    private Language language;
    private Keyword keyword;

    @BeforeClass
    public static void initialize() throws Exception
    {
        Properties properties = loadProperties("/jdbc.properties");
        EmbeddedDatabaseServer.startDatabase(properties);
        
        AnnotationConfiguration config = createConfiguration(properties);
        
        config.setListener("post-update", new AuditLogPostUpdateEventListener());
        config.setListener("post-insert", new AuditLogPostInsertEventListener());
        config.setListener("post-delete", new AuditLogPostDeleteEventListener());
        
        setSessionFactory(config.buildSessionFactory());
        
        User user = new User();
        user.setUsername("username");
        SecurityContextHolder.getContext().setAuthentication(
            new UsernamePasswordAuthenticationToken(user, "test"));
        initializeDataSource(properties);
        final Resource resource = new ClassPathResource("/hsql-schema.sql");
        executeSqlScript(resource);
    }
    
    @Before
    public final void setUp() throws Exception
    {
        super.setUp();
        
        Session session = getSession();
        Transaction tx = session.beginTransaction();

        // init data
        country = new Country();
        country.setCode(CountryCode.SG);
        country.setName("Singapore");
        
        bundle = new Bundle();
        bundle.setName("TongueTied Server");
        bundle.setDescription("tonguetied resources");
        bundle.setResourceName("tonguetied");

        bundle2 = new Bundle();
        bundle2.setName("test bundle");
        bundle2.setDescription("test");
        bundle2.setResourceName("test");

        language = new Language();
        language.setCode(LanguageCode.en);
        language.setName("English");
        session.saveOrUpdate(bundle);
        session.saveOrUpdate(bundle2);
        session.saveOrUpdate(country);
        session.saveOrUpdate(language);
        
        keyword = new Keyword();
        keyword.setContext("description of the keyword");
        keyword.setKeyword("existing");
        session.saveOrUpdate(keyword);
        
        Translation translation = new Translation();
        translation.setValue("existing value");
        translation.setState(TranslationState.UNVERIFIED);
        translation.setBundle(bundle);
        translation.setCountry(country);
        translation.setLanguage(language);
        translation.setKeyword(keyword);
        
        keyword.addTranslation(translation);
        session.saveOrUpdate(keyword);
        session.flush();
        tx.commit();
        session.close();
        
        session = getSession();
        tx = session.beginTransaction();
        
        Criteria criteria = session.createCriteria(AuditLogRecord.class);
        List<AuditLogRecord> records = criteria.list();
        assertEquals(2, records.size());
        tx.rollback();
        session.close();
    }

    @Test
    public final void testCreateEntity()
    {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        
        Keyword newKeyword = new Keyword();
        newKeyword.setContext("new keyword");
        newKeyword.setKeyword("new");
        session.saveOrUpdate(newKeyword);
        
        session.flush();
        session.close();
        assertTrue(tx.isActive());
        
        final String expectedNewValue = "context = new keyword\n"
            + "keyword = new\n"
            + "translations = null\n";
        
        session = getSession();
        tx = session.beginTransaction();
        Criteria criteria = session.createCriteria(AuditLogRecord.class);
        List<AuditLogRecord> records = criteria.list();
        assertEquals(3, records.size());
        AuditLogRecord newRecord = records.get(2);
        assertEquals(Operation.insert, newRecord.getMessage());
        assertEquals(expectedNewValue, newRecord.getNewValue());
        assertNull(newRecord.getOldValue());
        tx.rollback();
        session.close();
    }

    @Test
    public final void testCreateEntityWithNestedEntity()
    {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        
        Keyword newKeyword = new Keyword();
        newKeyword.setContext("new keyword");
        newKeyword.setKeyword("new");
//        session.saveOrUpdate(newKeyword);
        
        Translation translation = new Translation();
        translation.setValue("new value");
        translation.setState(TranslationState.VERIFIED);
        translation.setBundle(bundle);
        translation.setCountry(country);
        translation.setLanguage(language);
        translation.setKeyword(newKeyword);

        newKeyword.addTranslation(translation);
        session.saveOrUpdate(newKeyword);
        session.flush();
        session.close();
        assertTrue(tx.isActive());
        
        final String expectedNewValue = "context = new keyword\n"
            + "keyword = new\n"
            + "translations = "+translation.toLogString()+"\n\n";
        final String expectedTranslationValue = "bundle = " + bundle.getName() + "\n"
            + "country = " + country.getName() + "\n"
            + "keyword = " +newKeyword.toLogString()+ "\n"
            + "language = " +language.getName()+ "\n"
            + "state = "+ TranslationState.VERIFIED +"\n"
            + "value = new value\n";
        
        session = getSession();
        tx = session.beginTransaction();
        Criteria criteria = session.createCriteria(AuditLogRecord.class);
        List<AuditLogRecord> records = criteria.list();
        assertEquals(4, records.size());
        AuditLogRecord newRecord = records.get(2);
        assertEquals(Operation.insert, newRecord.getMessage());
        assertEquals(expectedNewValue, newRecord.getNewValue());
        assertNull(newRecord.getOldValue());
        newRecord = records.get(3);
        assertEquals(Operation.insert, newRecord.getMessage());
        assertEquals(expectedTranslationValue, newRecord.getNewValue());
        assertNull(newRecord.getOldValue());
        tx.rollback();
        session.close();
    }

    @Test
    public final void testCreateTranslationWithNullBundleCountryLanguage()
    {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        
        Translation translation = new Translation();
        translation.setValue("new value");
        translation.setState(TranslationState.VERIFIED);
        translation.setBundle(null);
        translation.setCountry(null);
        translation.setLanguage(null);
        translation.setKeyword(keyword);

        keyword.addTranslation(translation);
        session.saveOrUpdate(keyword);
        session.flush();
        session.close();
        assertTrue(tx.isActive());
        
        final String expectedNewValue = "bundle = " + null + "\n"
            + "country = " + null + "\n"
            + "keyword = " +keyword.toLogString()+ "\n"
            + "language = " +null+ "\n"
            + "state = "+ TranslationState.VERIFIED +"\n"
            + "value = new value\n";
//        final String expectedTranslationValue = "bundle = " + bundle.getName() + "\n"
//            + "country = " + country.getName() + "\n"
//            + "keyword = " +keyword.toLogString()+ "\n"
//            + "language = " +language.getName()+ "\n"
//            + "state = "+ TranslationState.VERIFIED +"\n"
//            + "value = new value\n";
//        
        session = getSession();
        tx = session.beginTransaction();
        Criteria criteria = session.createCriteria(AuditLogRecord.class);
        List<AuditLogRecord> records = criteria.list();
        assertEquals(5, records.size());
        AuditLogRecord newRecord = records.get(2);
        assertEquals(Operation.insert, newRecord.getMessage());
        assertEquals(expectedNewValue, newRecord.getNewValue());
        assertNull(newRecord.getOldValue());
        newRecord = records.get(3);
        assertEquals(Operation.update, newRecord.getMessage());
//        assertEquals(expectedTranslationValue, newRecord.getNewValue());
        assertNull(newRecord.getOldValue());
        newRecord = records.get(4);
        assertEquals(Operation.update, newRecord.getMessage());
//        assertEquals(expectedTranslationValue, newRecord.getNewValue());
        assertNull(newRecord.getOldValue());
        tx.rollback();
        session.close();
    }

    @Test
    public final void testSaveUnchangedEntity()
    {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        
        Keyword reloaded = 
            (Keyword) session.get(Keyword.class, keyword.getId());
        session.saveOrUpdate(reloaded);
        session.flush();
        session.close();
        assertTrue(tx.isActive());
        
        session = getSession();
        tx = session.beginTransaction();
        Criteria criteria = session.createCriteria(AuditLogRecord.class);
        List<AuditLogRecord> records = criteria.list();
        assertEquals(2, records.size());
        tx.rollback();
        session.close();
    }

    @Test
    public final void testUpdateEntity()
    {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        
        Keyword reloaded = 
            (Keyword) session.get(Keyword.class, keyword.getId());
        final String expectedOldValue = "keyword = existing\n";
        reloaded.setKeyword("updated");
        session.saveOrUpdate(reloaded);
        final String expectedNewValue = "keyword = updated\n";
        session.flush();
        session.close();
        assertTrue(tx.isActive());
        
        session = getSession();
        tx = session.beginTransaction();
        Criteria criteria = session.createCriteria(AuditLogRecord.class);
        List<AuditLogRecord> records = criteria.list();
        assertEquals(3, records.size());
        AuditLogRecord newRecord = records.get(2);
        assertEquals(Operation.update, newRecord.getMessage());
        assertEquals(expectedNewValue, newRecord.getNewValue());
        assertEquals(expectedOldValue, newRecord.getOldValue());
        tx.rollback();
        session.close();
    }

    @Test
    public final void testUpdateNestedEntity()
    {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        
        Keyword reloaded = 
            (Keyword) session.get(Keyword.class, keyword.getId());
        final String expectedOldValue = "bundle = "+bundle.getName()+"\nvalue = existing value\n";
        final String expectedNewValue = "bundle = "+bundle2.getName()+"\nvalue = updated value\n";
        reloaded.getTranslations().first().setBundle(bundle2);
        reloaded.getTranslations().first().setValue("updated value");
        session.saveOrUpdate(reloaded);
        session.flush();
        session.close();
        assertTrue(tx.isActive());
        
        session = getSession();
        tx = session.beginTransaction();
        Criteria criteria = session.createCriteria(AuditLogRecord.class);
        List<AuditLogRecord> records = criteria.list();
        assertEquals(3, records.size());
        AuditLogRecord newRecord = records.get(2);
        assertEquals(Operation.update, newRecord.getMessage());
        assertEquals(expectedOldValue, newRecord.getOldValue());
        assertEquals(expectedNewValue, newRecord.getNewValue());
        tx.rollback();
        session.close();
    }

    @Test
    public final void testDeleteNestedEntity()
    {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        assertTrue(tx.isActive());
        
        Keyword reloaded = 
            (Keyword) session.get(Keyword.class, keyword.getId());
        final Translation translation = reloaded.getTranslations().first();
        final Long translationId = translation.getId();
        final String logValue = "translations = " + translation.toLogString() + "\n\n";
        reloaded.removeTranslation(translationId);
        session.saveOrUpdate(reloaded);
        session.flush();
        session.close();
        
        session = getSession();
        tx = session.beginTransaction();
        Criteria criteria = session.createCriteria(AuditLogRecord.class);
        List<AuditLogRecord> records = criteria.list();
        assertEquals(3, records.size());
        AuditLogRecord newRecord = records.get(2);
        assertEquals(Operation.update, newRecord.getMessage());
        assertEquals("translations = null\n", newRecord.getNewValue());
        assertEquals(logValue, newRecord.getOldValue());
        tx.rollback();
        session.close();
    }

    @Test
    public final void testAddNestedEntity()
    {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        
        Keyword reloaded = 
            (Keyword) session.get(Keyword.class, keyword.getId());
        Translation translation = 
            new Translation(bundle2, country, language, "value", TranslationState.VERIFIED);
        reloaded.addTranslation(translation);
        final String expectedTranslationValue = "bundle = " + bundle2.getName() + "\n"
            + "country = " + country.getName() + "\n"
            + "keyword = "+reloaded.toLogString()+"\n"
            + "language = " +language.getName() + "\n"
            + "state = "+ TranslationState.VERIFIED +"\n"
            + "value = value\n";
        session.saveOrUpdate(reloaded);
        session.flush();
        session.close();
        assertTrue(tx.isActive());
        
        session = getSession();
        tx = session.beginTransaction();
        Criteria criteria = session.createCriteria(AuditLogRecord.class);
        List<AuditLogRecord> records = criteria.list();
        assertEquals(4, records.size());
        AuditLogRecord newRecord = records.get(2);
        assertEquals(Operation.insert, newRecord.getMessage());
        assertEquals(expectedTranslationValue, newRecord.getNewValue());
        assertNull(newRecord.getOldValue());
        newRecord = records.get(3);
        assertEquals(Operation.update, newRecord.getMessage());
        assertEquals("", newRecord.getNewValue());
        assertEquals("", newRecord.getOldValue());
        tx.rollback();
        session.close();
    }

    @Test
    public final void testDeleteEntity()
    {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        
        Keyword reloaded = 
            (Keyword) session.get(Keyword.class, keyword.getId());
        final String translationLogString = "bundle = "+bundle.getName()+"\n"
            + "country = "+country.getName() +"\n"
            + "keyword = "+keyword.toLogString()+"\n"
            + "language = "+language.getName() +"\n"
            + "state = "+TranslationState.UNVERIFIED+"\n"
            + "value = existing value\n";
        session.delete(reloaded);
        session.flush();
        session.close();
        assertTrue(tx.isActive());
        
        session = getSession();
        tx = session.beginTransaction();
        Criteria criteria = session.createCriteria(AuditLogRecord.class);
        List<AuditLogRecord> records = criteria.list();
        assertEquals(4, records.size());
        AuditLogRecord newRecord = records.get(2);
        assertEquals(Operation.delete, newRecord.getMessage());
        assertEquals(translationLogString, newRecord.getOldValue());
        assertNull(newRecord.getNewValue());
        newRecord = records.get(3);
        assertEquals(Operation.delete, newRecord.getMessage());
        tx.rollback();
        session.close();
    }

    @Override
    protected String[] getTableNames()
    {
        return new String[] {TABLE_AUDIT_LOG_RECORD, TABLE_TRANSLATION, TABLE_KEYWORD, TABLE_BUNDLE, 
                TABLE_COUNTRY, TABLE_LANGUAGE};
    }
}
