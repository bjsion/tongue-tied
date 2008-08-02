package org.tonguetied.datatransfer.exporting;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.tonguetied.datatransfer.common.FormatType;

/**
 * @author bsion
 *
 */
@RunWith(value=Parameterized.class)
public class ExportDataPostProcessorFactoryTest {
    
    private FormatType formatType;
    private Class<? extends ExportDataPostProcessor> expectedClass;

    @Parameters
    public static final Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {FormatType.xls, null},
                {FormatType.xlsLanguage, LanguageCentricProcessor.class},
                {FormatType.properties, null},
                {FormatType.csv, null},
                {FormatType.resx, null}
        });
    }

    /**
     * Create a new instance of ExportDataPostProcessorFactoryTest.
     * 
     * @param formatType the {@link FormatType} to evaluate
     * @param expectedClass the expected class type that should be created
     */
    public ExportDataPostProcessorFactoryTest(FormatType formatType,
            Class<? extends ExportDataPostProcessor> expectedClass) {
        this.formatType = formatType;
        this.expectedClass = expectedClass;
    }

    /**
     * Test method for {@link org.tonguetied.datatransfer.exporting.ExportDataPostProcessorFactory#getPostProcessor(FormatType)}.
     */
    @Test
    public final void testCreatePostProcessor() {
        ExportDataPostProcessor postProcessor = ExportDataPostProcessorFactory.getPostProcessor(formatType);
        if (expectedClass == null) {
            assertNull(postProcessor);
        }
        else {
            assertEquals(expectedClass, postProcessor.getClass());
        }
    }

}
