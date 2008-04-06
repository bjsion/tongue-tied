package org.tonguetied.architecture;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

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
    
    private static final String[] PACKAGE_FILTER = new String[] {
        "fmpp*", "freemarker*", "java.*", "javax.*", "org.acegisecurity*",
        "org.apache.*", "org.hibernate*", "org.mortbay.*",
        "org.springframework.*"
    };

    @Before
    public void setUp() throws Exception {
        PackageFilter filter = new PackageFilter(Arrays.asList(PACKAGE_FILTER));
        
        this.jDepend = new JDepend(filter);
        
        jDepend.addDirectory("D:/work/TongueTied/Application/classes");
        jDepend.addDirectory("D:/work/TongueTied/Server/classes");
//        jDepend.addDirectory("D:/work/TongueTied/Build3rdParty/Spring/2.5");
//        jDepend.addDirectory("D:/work/TongueTied/Build3rdParty/Spring/acegi-security/1.0.4");
//        jDepend.addDirectory("D:/work/TongueTied/Build3rdParty/Hibernate/3.2.0");
//        jDepend.addDirectory("D:/work/TongueTied/Build3rdParty/Hibernate/annotations/3.2.0");
//        jDepend.addDirectory("D:/work/TongueTied/Build3rdParty/FreeMarker/2.3.9");
//        jDepend.addDirectory("D:/work/TongueTied/Build3rdParty/FreeMarker/FMPP/0.9.11");
//        jDepend.addDirectory("D:/work/TongueTied/Build3rdParty/Jetty/6.1.1");
//        jDepend.addDirectory("");
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
        
        p = jDepend.getPackage("org.tonguetied.web.servlet");
        
        assertNotNull(p);
        assertFalse("Cycle exists: " + p.getName(), p.containsCycle());
    }
    
    /**
     * Tests that a package dependency cycle does not exist for any of the 
     * analyzed packages.
     */
    @Test
    public void testCyclicDependency() {
        Collection<?> packages = jDepend.analyze();

        assertFalse(packages.isEmpty());
        assertFalse("Cycles exist. Check code to remove cycles between packages", jDepend.containsCycles());
    }
}
