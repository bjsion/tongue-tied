package org.tonguetied.usermanagement;

import org.tonguetied.usermanagement.UserRight;
import org.tonguetied.usermanagement.UserRight.Permission;

import junitx.extensions.ComparabilityTestCase;

/**
 * Test class to ensure the {@link UserRight} class is compliant with the 
 * <code>Comparable</code> interface.
 * 
 * @author bsion
 *
 */
public class UserRightComparabilityTest extends ComparabilityTestCase
{
    
    /**
     * @param name
     */
    public UserRightComparabilityTest(String name)
    {
        super(name);
    }

    /* (non-Javadoc)
     * @see junitx.extensions.ComparabilityTestCase#createEqualInstance()
     */
    @Override
    protected Comparable<UserRight> createEqualInstance() throws Exception
    {
        UserRight userRight = new UserRight(Permission.ROLE_USER, null, null, null);
        return userRight;
    }

    /* (non-Javadoc)
     * @see junitx.extensions.ComparabilityTestCase#createGreaterInstance()
     */
    @Override
    protected Comparable<UserRight> createGreaterInstance() throws Exception
    {
        UserRight userRight = new UserRight(Permission.ROLE_VERIFIER, null, null, null);
        return userRight;
    }

    /* (non-Javadoc)
     * @see junitx.extensions.ComparabilityTestCase#createLessInstance()
     */
    @Override
    protected Comparable<UserRight> createLessInstance() throws Exception
    {
        UserRight userRight = new UserRight(Permission.ROLE_ADMIN, null, null, null);
        return userRight;
    }

}
