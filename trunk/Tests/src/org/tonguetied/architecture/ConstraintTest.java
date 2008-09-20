package org.tonguetied.architecture;

import static org.junit.Assert.assertEquals;
import static org.tonguetied.architecture.Constants.APPLICATION_CLASSES;
import static org.tonguetied.architecture.Constants.PACKAGE_FILTER;
import static org.tonguetied.architecture.Constants.SERVER_CLASSES;
import jdepend.framework.DependencyConstraint;
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
public class ConstraintTest
{
    private JDepend jDepend;

    @Before
    public void setUp() throws Exception
    {
        // PackageFilter filter = new
        // PackageFilter(Arrays.asList(PACKAGE_FILTER));
        PackageFilter filter = new PackageFilter();
        for (String packageName : PACKAGE_FILTER)
        {
            filter.addPackage(packageName);
        }

        this.jDepend = new JDepend(filter);

        jDepend.addDirectory(APPLICATION_CLASSES.getAbsolutePath());
        jDepend.addDirectory(SERVER_CLASSES.getAbsolutePath());
    }

    /**
     * Tests that the package dependency constraint is met for the analyzed
     * packages.
     */
    @Test
    public final void testPackageDependencies()
    {
        DependencyConstraint constraint = new DependencyConstraint();

        JavaPackage audit = constraint.addPackage("org.tonguetied.audit");
        JavaPackage keywordmanagement = constraint
                .addPackage("org.tonguetied.keywordmanagement");
        JavaPackage datatransfer = constraint
                .addPackage("org.tonguetied.datatransfer");
        JavaPackage datatransferDao = constraint
                .addPackage("org.tonguetied.datatransfer.dao");
        JavaPackage datatransferCommon = constraint
                .addPackage("org.tonguetied.datatransfer.common");
        JavaPackage datatransferExport = constraint
                .addPackage("org.tonguetied.datatransfer.exporting");
        JavaPackage datatransferImport = constraint
                .addPackage("org.tonguetied.datatransfer.importing");
        JavaPackage usermanagement = constraint
                .addPackage("org.tonguetied.usermanagement");
        JavaPackage web = constraint.addPackage("org.tonguetied.web");
        JavaPackage webServlet = constraint
                .addPackage("org.tonguetied.web.servlet");
        JavaPackage dao = constraint.addPackage("org.tonguetied.dao");
        JavaPackage server = constraint.addPackage("org.tonguetied.server");

        keywordmanagement.dependsUpon(audit);
        usermanagement.dependsUpon(keywordmanagement);
        datatransferCommon.dependsUpon(keywordmanagement);
        datatransferDao.dependsUpon(datatransferCommon);
        datatransferDao.dependsUpon(keywordmanagement);
        datatransferExport.dependsUpon(datatransferCommon);
        datatransferExport.dependsUpon(datatransferDao);
        datatransferExport.dependsUpon(keywordmanagement);
        datatransferImport.dependsUpon(datatransferCommon);
        datatransferImport.dependsUpon(keywordmanagement);
        datatransfer.dependsUpon(datatransferCommon);
        datatransfer.dependsUpon(datatransferDao);
        datatransfer.dependsUpon(datatransferExport);
        datatransfer.dependsUpon(datatransferImport);
        datatransfer.dependsUpon(keywordmanagement);
        web.dependsUpon(usermanagement);
        web.dependsUpon(datatransfer);
        web.dependsUpon(datatransferCommon);
        web.dependsUpon(keywordmanagement);
        web.dependsUpon(audit);

        jDepend.analyze();

        assertEquals("Dependency mismatch", true, jDepend
                .dependencyMatch(constraint));
    }
}
