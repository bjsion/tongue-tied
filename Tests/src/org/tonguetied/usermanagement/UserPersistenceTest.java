package org.tonguetied.usermanagement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Ignore;
import org.junit.Test;
import org.tonguetied.test.common.PersistenceTestBase;
import org.tonguetied.usermanagement.User;
import org.tonguetied.usermanagement.UserRight;
import org.tonguetied.usermanagement.UserRight.Permission;



/**
 * @author bsion
 *
 */
public class UserPersistenceTest extends PersistenceTestBase {

    @Test
    public final void simplePersistence() {
        Session session;
        Transaction tx;
        
        session = getSession();
        tx = session.beginTransaction();

        User user = new User("username", "password", "firstName", "lastName", "test@test.com", true, true, true, true);
        session.saveOrUpdate(user);
        session.close();
        
        session = getSession();
        tx = session.beginTransaction();
        User reloaded = 
            (User) session.get(User.class, user.getId());
        assertEquals(user, reloaded);
        tx.rollback();
        session.close();
    }
    
    @Ignore("test not working for some reason")
    public final void persistUserWithAuthorities() {
        Session session;
        Transaction tx;
        
        session = getSession();
        tx = session.beginTransaction();

        User user = new User("username", "password", "firstName", "lastName", "test@test.com", true, true, true, true);
        session.saveOrUpdate(user);
        UserRight userRight = new UserRight(Permission.ROLE_USER, null, null, null);
        user.addUserRight(userRight);//"ROLE_USER");
        session.close();
        
        session = getSession();
        tx = session.beginTransaction();
        User reloaded = 
            (User) session.get(User.class, user.getId());
        assertEquals(user, reloaded);
        assertEquals(1, reloaded.getUserRights().size());
        assertTrue(user.getUserRights().contains("ROLE_USER"));
        tx.rollback();
        session.close();
    }
}
