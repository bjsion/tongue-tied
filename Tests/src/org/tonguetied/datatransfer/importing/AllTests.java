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
package org.tonguetied.datatransfer.importing;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suite for package.
 * 
 * @author bsion
 *
 */
@RunWith(Suite.class)
@SuiteClasses({
    CsvImporterTest.class,
    ExcelLanguageCentricParserTest.class,
    ExcelImporterTest.class,
    JavaFxPropertiesImporterTest.class,
    ImporterFactoryTest.class,
    ImporterTest.class,
    ImporterUtilsTest.class,
    KeywordExcelParserTest.class,
    PropertiesImporterTest.class,
    ResourceImporterTest.class
})
public class AllTests {

    public static Test suite() {
        return new JUnit4TestAdapter(AllTests.class);
    }
}
