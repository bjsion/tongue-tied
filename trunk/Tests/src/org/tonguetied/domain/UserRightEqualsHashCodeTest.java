package org.tonguetied.domain;

import org.tonguetied.domain.UserRight.Permission;

import junitx.extensions.EqualsHashCodeTestCase;


/**
 * @author bsion
 *
 */
public class UserRightEqualsHashCodeTest extends EqualsHashCodeTestCase {

    public UserRightEqualsHashCodeTest(String name) {
        super(name);
    }

    @Override
    protected Object createInstance() throws Exception {
        UserRight userRight = new UserRight(Permission.ROLE_USER, null, null, null);
        
        return userRight;
    }

    @Override
    protected Object createNotEqualInstance() throws Exception {
        UserRight userRight = new UserRight(Permission.ROLE_VERIFIER, null, null, null);
        
        return userRight;
    }
}
