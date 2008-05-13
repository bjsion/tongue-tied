package org.tonguetied.datatransfer.exporting;

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
    ExportDataPostProcessorFactoryTest.class,
    LanguageCentricProcessorTest.class,
    Native2AsciiDirectiveTest.class,
    Native2AsciiWriterTest.class
})
public class AllTests {

    public static Test suite() {
        return new JUnit4TestAdapter(AllTests.class);
    }
}
