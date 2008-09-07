package org.tonguetied.architecture;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.tonguetied.architecture.Constants.APPLICATION_CLASSES;
import static org.tonguetied.architecture.Constants.PACKAGE_FILTER;
import static org.tonguetied.architecture.Constants.SERVER_CLASSES;

import java.util.Arrays;
import java.util.Collection;

import jdepend.framework.JDepend;
import jdepend.framework.JavaPackage;
import jdepend.framework.PackageFilter;

import org.junit.Before;
import org.junit.Test;

/**
 * This test case is designed to catch cyclic references between packages 
 * thereby enforcing architectural designs.
 * 
 * @author bsion
 *
 */
public class CycleTest {

    private JDepend jDepend;
    
    @Before
    public void setUp() throws Exception {
        PackageFilter filter = new PackageFilter(Arrays.asList(PACKAGE_FILTER));
        
        this.jDepend = new JDepend(filter);
        
        jDepend.addDirectory(APPLICATION_CLASSES.getAbsolutePath());
        jDepend.addDirectory(SERVER_CLASSES.getAbsolutePath());
    }

    /**
     * Tests that a single package does not contain any package dependency 
     * cycles.
     */
    @Test
    public final void testOnePackage() {
        jDepend.analyze();
        
        JavaPackage p = jDepend.getPackage("org.tonguetied.server");
        
        assertNotNull(p);
        assertFalse("Cycle exists: " + p.getName(), p.containsCycle());
        
        p = jDepend.getPackage("org.tonguetied.web.servlet");
        
        assertNotNull(p);
        assertFalse("Cycle exists: " + p.getName(), p.containsCycle());
    }
    
    /**
     * Tests that a package dependency cycle does not exist for any of the 
     * analyzed packages.
     */
    @Test
    public final void testCyclicDependency() {
        Collection<?> packages = jDepend.analyze();

        assertFalse(packages.isEmpty());
        assertFalse("Cycles exist. Check code to remove cycles between packages", jDepend.containsCycles());
    }
}
