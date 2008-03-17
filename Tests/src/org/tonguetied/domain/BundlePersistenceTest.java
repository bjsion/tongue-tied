package org.tonguetied.domain;

import static org.junit.Assert.assertEquals;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

/**
 * @author bsion
 *
 */
public class BundlePersistenceTest extends PersistenceTestBase {

    @Test
    public final void simplePersistence() {
        Session session;
        Transaction tx;
        
        session = getSession();
        tx = session.beginTransaction();

        Bundle bundle = new Bundle();
        bundle.setName("tonguetied Server");
        bundle.setDescription("tonguetied resources");
        bundle.setResourceDestination("/home");
        bundle.setResourceName("tonguetied");
        session.saveOrUpdate(bundle);
        session.close();
        
        session = getSession();
        tx = session.beginTransaction();
        Bundle reloaded =  (Bundle) session.get(Bundle.class, bundle.getId());
        assertEquals(bundle, reloaded);
        tx.rollback();
        session.close();
    }
}
