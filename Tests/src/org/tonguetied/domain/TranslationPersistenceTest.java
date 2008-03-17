package org.tonguetied.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;
import org.tonguetied.domain.Country.CountryCode;
import org.tonguetied.domain.Language.LanguageCode;
import org.tonguetied.domain.Translation.TranslationState;



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

        // init data
        Bundle bundle = new Bundle();
        bundle.setName("tonguetied Server");
        bundle.setDescription("tonguetied resources");
        bundle.setResourceDestination("/home");
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
