package org.tonguetied.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.GenericJDBCException;
import org.junit.Test;
import org.tonguetied.domain.Country.CountryCode;
import org.tonguetied.domain.Language.LanguageCode;
import org.tonguetied.domain.Translation.TranslationState;



/**
 * @author bsion
 *
 */
public class KeywordPersistenceTest extends PersistenceTestBase {

    @Test
    public final void simplePersistence() {
        Session session;
        Transaction tx;
        
        session = getSession();
        tx = session.beginTransaction();

        Keyword keyword = new Keyword();
        keyword.setContext("description of the keyword");
        keyword.setKeyword("testKeyword");
        session.saveOrUpdate(keyword);
        session.close();
        
        session = getSession();
        tx = session.beginTransaction();
        Keyword reloaded = 
            (Keyword) session.get(Keyword.class, keyword.getId());
        assertEquals(keyword, reloaded);
        tx.rollback();
        session.close();
    }

    @Test
    public final void persistKeywordWithTranslations() {
        Session session;
        Transaction tx;
        
        session = getSession();
        tx = session.beginTransaction();

        // init data
        Country country = new Country();
        country.setCode(CountryCode.SG);
        country.setName("Singapore");
        
        Bundle bundle = new Bundle();
        bundle.setName("TongueTied Server");
        bundle.setDescription("tonguetied resources");
        bundle.setResourceDestination("/home");
        bundle.setResourceName("tonguetied");

        Language language = new Language();
        language.setCode(LanguageCode.en);
        language.setName("English");
        session.saveOrUpdate(country);
        session.saveOrUpdate(bundle);
        session.saveOrUpdate(language);

        Keyword keyword = new Keyword();
        keyword.setContext("description of the keyword");
        keyword.setKeyword("testKeyword");
        session.saveOrUpdate(keyword);
        
        Translation translation = new Translation();
        translation.setValue("translated value");
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
        Keyword reloaded = 
            (Keyword) session.get(Keyword.class, keyword.getId());
//        Translation rt = 
//            (Translation) session.get(Translation.class, translation.getId()); 
//        assertEquals(translation, rt);
        assertEquals(1, reloaded.getTranslations().size());
        assertEquals(keyword, reloaded);
        assertTrue(reloaded.getTranslations().contains(translation));
        tx.rollback();
        session.close();
    }
    
    @Test(expected=GenericJDBCException.class)
    public final void persistKeywordWithTranslationsWithSameBusinesskey() {
        Session session;
        Transaction tx;
        
        session = getSession();
        tx = session.beginTransaction();

        // init data
        Country country = new Country();
        country.setCode(CountryCode.SG);
        country.setName("Singapore");
        
        Bundle bundle = new Bundle();
        bundle.setName("tonguetied Server");
        bundle.setDescription("tonguetied resources");
        bundle.setResourceDestination("/home");
        bundle.setResourceName("tonguetied");

        Language language = new Language();
        language.setCode(LanguageCode.en);
        language.setName("English");
        session.saveOrUpdate(country);
        session.saveOrUpdate(bundle);
        session.saveOrUpdate(language);

        Keyword keyword = new Keyword();
        keyword.setContext("description of the keyword");
        keyword.setKeyword("testKeyword");
        session.saveOrUpdate(keyword);
        
        Translation translation = new Translation();
        translation.setValue("translated value");
        translation.setState(TranslationState.VERIFIED);
        translation.setBundle(bundle);
        translation.setCountry(country);
        translation.setLanguage(language);
//        translation.setKeyword(keyword);
        keyword.addTranslation(translation);
        
        session.saveOrUpdate(keyword);
        session.flush();
        tx.commit();
        session.close();
        
        session = getSession();
        tx = session.beginTransaction();
        Translation translation2 = new Translation();
        translation2.setValue("another translated value");
        translation2.setState(TranslationState.VERIFIED);
        translation2.setBundle(bundle);
        translation2.setCountry(country);
        translation2.setLanguage(language);
//        translation2.setKeyword(keyword);
        
        keyword.addTranslation(translation2);
        session.saveOrUpdate(keyword);
        session.flush();
        tx.commit();
//        tx.rollback();
        session.close();
    }
}