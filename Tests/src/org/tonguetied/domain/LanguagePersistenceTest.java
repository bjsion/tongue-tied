package org.tonguetied.domain;

import static org.junit.Assert.assertEquals;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;
import org.tonguetied.domain.Language.LanguageCode;



/**
 * @author bsion
 *
 */
public class LanguagePersistenceTest extends PersistenceTestBase {

    @Test
    public final void simplePersistence() {
        Session session;
        Transaction tx;
        
        session = getSession();
        tx = session.beginTransaction();

        Language language = new Language();
        language.setCode(LanguageCode.en);
        language.setName("English");
        session.saveOrUpdate(language);
        session.close();
        
        session = getSession();
        tx = session.beginTransaction();
        Language reloaded = 
            (Language) session.get(Language.class, language.getId());
        assertEquals(language, reloaded);
        tx.rollback();
        session.close();
    }
}
