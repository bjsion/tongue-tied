package org.tonguetied.service;

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
    AuditServiceTest.class,
    ExcelDataParserTest.class,
    ExcelImporterTest.class,
    ExportServiceTest.class,
    LanguageCentricProcessorTest.class,
    AppilcationServiceTest.class,
    TranslationPredicateTest.class,
    UserDetailsServiceTest.class,
    UserServiceTest.class
})
public class AllTests {

    public static Test suite() {
        return new JUnit4TestAdapter(AllTests.class);
    }
}
