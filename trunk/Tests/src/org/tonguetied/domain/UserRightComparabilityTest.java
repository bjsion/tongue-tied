package org.tonguetied.domain;

import org.tonguetied.domain.UserRight.Permission;

import junitx.extensions.ComparabilityTestCase;

public class UserRightComparabilityTest extends ComparabilityTestCase {
    
    /**
     * @param name
     */
    public UserRightComparabilityTest(String name) {
        super(name);
    }

    /* (non-Javadoc)
     * @see junitx.extensions.ComparabilityTestCase#createEqualInstance()
     */
    @Override
    protected Comparable<?> createEqualInstance() throws Exception {
        UserRight userRight = new UserRight(Permission.ROLE_USER, null, null, null);
        return userRight;
    }

    /* (non-Javadoc)
     * @see junitx.extensions.ComparabilityTestCase#createGreaterInstance()
     */
    @Override
    protected Comparable<?> createGreaterInstance() throws Exception {
        UserRight userRight = new UserRight(Permission.ROLE_VERIFIER, null, null, null);
        return userRight;
    }

    /* (non-Javadoc)
     * @see junitx.extensions.ComparabilityTestCase#createLessInstance()
     */
    @Override
    protected Comparable<? extends Object> createLessInstance() throws Exception {
        UserRight userRight = new UserRight(Permission.ROLE_ADMIN, null, null, null);
        return userRight;
    }

}
