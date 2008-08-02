package org.tonguetied.datatransfer.common;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import org.tonguetied.datatransfer.common.FormatType;

/**
 * Test class for the methods of the {@link FormatType} enum.
 * 
 * @author bsion
 *
 */
@RunWith(value=Parameterized.class)
public class FormatTypeTest {
    
    private FormatType formatType;
    private String expected;

    @Parameters
    public static final Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {FormatType.xls, "xls"},
                {FormatType.xlsLanguage, "xls"},
                {FormatType.properties, "properties"},
                {FormatType.csv, "csv"},
                {FormatType.resx, "resx"}
        });
    }

    /**
     * Create a new instance of FormatTypeTest.
     * 
     * @param formatType the {@link FormatType} to evaluate
     * @param expected the expected class type that should be created
     */
    public FormatTypeTest(FormatType formatType, String expected) {
        this.formatType = formatType;
        this.expected = expected;
    }

    /**
     * Test method for {@link FormatType#getDefaultFileExtension()}.
     */
    @Test
    public final void testCreateImporter() {
        String actual = formatType.getDefaultFileExtension();
        assertEquals(expected, actual);
    }

}
