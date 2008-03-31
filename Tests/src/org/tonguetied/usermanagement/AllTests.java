package org.tonguetied.usermanagement;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * @author bsion
 *
 */
@RunWith(Suite.class)
@SuiteClasses({
    UserDetailsServiceTest.class,
    UserEqualsHashCodeTest.class,
    UserPersistenceTest.class,
    UserRightComparabilityTest.class,
    UserRightEqualsHashCodeTest.class,
    UserRightSerializabilityTest.class,
    UserServiceTest.class,
    UserTest.class
})
public class AllTests {

    public static Test suite() {
        return new JUnit4TestAdapter(AllTests.class);
    }
}
