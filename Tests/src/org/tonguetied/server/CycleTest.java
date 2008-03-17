package org.tonguetied.server;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.Collection;

import jdepend.framework.JDepend;
import jdepend.framework.JavaPackage;

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
        this.jDepend = new JDepend();
        
        jDepend.addDirectory("C:/work/TongueTied/Server/classes");
    }

    /**
     * Tests that a single package does not contain any package dependency 
     * cycles.
     */
    @Test
    public void testOnePackage() {
        jDepend.analyze();
        
        JavaPackage p = jDepend.getPackage("org.tonguetied.server");
        
        assertNotNull(p);
        assertFalse("Cycle exists: " + p.getName(), p.containsCycle());
    }
    
    /**
     * Tests that a package dependency cycle does not exist for any of the 
     * analyzed packages.
     */
    @Test
    public void testAllPackages() {
        Collection<?> packages = jDepend.analyze();

        assertFalse(packages.isEmpty());
        assertFalse("Cycles exist", jDepend.containsCycles());
    }
}
