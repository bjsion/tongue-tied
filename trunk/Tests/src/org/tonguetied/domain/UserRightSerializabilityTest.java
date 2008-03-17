package org.tonguetied.domain;

import java.io.Serializable;

import org.tonguetied.domain.UserRight.Permission;


import junitx.extensions.SerializabilityTestCase;

/**
 * @author bsion
 *
 */
public class UserRightSerializabilityTest extends SerializabilityTestCase {

    /**
     * @param name
     */
    public UserRightSerializabilityTest(String name) {
        super(name);
    }

    /* (non-Javadoc)
     * @see junitx.extensions.SerializabilityTestCase#createInstance()
     */
    @Override
    protected Serializable createInstance() throws Exception {
        UserRight userRight = new UserRight(Permission.ROLE_VERIFIER, null, null, null);
        return userRight;
    }

}
