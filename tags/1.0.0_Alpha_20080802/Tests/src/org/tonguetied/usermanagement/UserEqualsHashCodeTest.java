package org.tonguetied.usermanagement;

import java.util.SortedSet;
import java.util.TreeSet;

import org.tonguetied.usermanagement.User;
import org.tonguetied.usermanagement.UserRight;
import org.tonguetied.usermanagement.UserRight.Permission;

import junitx.extensions.EqualsHashCodeTestCase;


/**
 * @author bsion
 *
 */
public class UserEqualsHashCodeTest extends EqualsHashCodeTestCase {

    public UserEqualsHashCodeTest(String name) {
        super(name);
    }

    @Override
    protected Object createInstance() throws Exception {
        SortedSet<UserRight> userRights = new TreeSet<UserRight>();
        userRights.add(new UserRight(Permission.ROLE_USER, null, null, null));
        User user = new User("username","password", "firstname", "lastname", "test@test.com", true, true, true, true);
        user.setUserRights(userRights);
        
        return user;
    }

    @Override
    protected Object createNotEqualInstance() throws Exception {
        SortedSet<UserRight> userRights = new TreeSet<UserRight>();
        userRights.add(new UserRight(Permission.ROLE_USER, null, null, null));
        User user = new User("username","password", "firstname", "Smith", "test@test.com", true, true, true, true); 
        user.setUserRights(userRights);
        
        return user;
    }
}
