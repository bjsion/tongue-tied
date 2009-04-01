/*
 * Copyright 2008 The Tongue-Tied Authors
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not 
 * use this file except in compliance with the License. You may obtain a copy 
 * of the License at
 *  
 * http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the 
 * License for the specific language governing permissions and limitations 
 * under the License. 
 */
package org.tonguetied.architecture;

import static org.junit.Assert.*;
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

        JavaPackage administration = constraint.addPackage("org.tonguetied.administration");
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
        JavaPackage utilsDatabase = 
            constraint.addPackage("org.tonguetied.utils.database");
        JavaPackage web = constraint.addPackage("org.tonguetied.web");
        JavaPackage webServlet = constraint
                .addPackage("org.tonguetied.web.servlet");
        JavaPackage dao = constraint.addPackage("org.tonguetied.dao");
        JavaPackage server = constraint.addPackage("org.tonguetied.server");
        
        assertNotNull(server);
        assertNotNull(dao);
        assertNotNull(administration);

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
        webServlet.dependsUpon(utilsDatabase);
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
