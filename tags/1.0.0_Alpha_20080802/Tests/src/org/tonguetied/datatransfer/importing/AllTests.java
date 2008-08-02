package org.tonguetied.datatransfer.importing;

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
    ExcelDataParserTest.class,
    ExcelImporterTest.class,
    ImporterFactoryTest.class,
    ImporterTest.class,
    PropertiesImporterTest.class,
    TranslationPredicateTest.class
})
public class AllTests {

    public static Test suite() {
        return new JUnit4TestAdapter(AllTests.class);
    }
}
