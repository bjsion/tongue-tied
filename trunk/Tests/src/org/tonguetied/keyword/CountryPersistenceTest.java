package org.tonguetied.keywordmanagement;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;
import org.tonguetied.keywordmanagement.Country;
import org.tonguetied.keywordmanagement.Country.CountryCode;
import org.tonguetied.test.common.PersistenceTestBase;


import static org.junit.Assert.*;

/**
 * @author bsion
 *
 */
public class CountryPersistenceTest extends PersistenceTestBase {
    
    @Test
    public final void simplePersistence() {
        Session session;
        Transaction tx;
        
        session = getSession();
        tx = session.beginTransaction();
        
        Country country = new Country();
        country.setCode(CountryCode.SG);
        country.setName("Singapore");
        session.saveOrUpdate(country);
        session.close();
        
        session = getSession();
        tx = session.beginTransaction();
        Country reloaded = 
            (Country) session.get(Country.class, country.getId());
        assertEquals(country, reloaded);
        tx.rollback();
        session.close();
    }

}
