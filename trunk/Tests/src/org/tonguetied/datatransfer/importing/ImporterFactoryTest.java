package org.tonguetied.datatransfer.importing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.tonguetied.datatransfer.common.FormatType;
import org.tonguetied.keywordmanagement.KeywordServiceStub;

/**
 * @author bsion
 *
 */
@RunWith(value=Parameterized.class)
public class ImporterFactoryTest {
    
    private FormatType formatType;
    private Class<? extends Importer> expectedClass;

    @Parameters
    public static final Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {FormatType.xls, ExcelImporter.class},
                {FormatType.xlsLanguage, ExcelImporter.class},
                {FormatType.properties, PropertiesImporter.class},
                {FormatType.csv, CsvImporter.class},
                {FormatType.resx, ResourceImporter.class}
        });
    }

    /**
     * Create a new instance of ExportDataPostProcessorFactoryTest.
     * 
     * @param formatType the {@link FormatType} to evaluate
     * @param expectedClass the expected class type that should be created
     */
    public ImporterFactoryTest(FormatType formatType,
            Class<? extends Importer> expectedClass) {
        this.formatType = formatType;
        this.expectedClass = expectedClass;
    }

    /**
     * Test method for {@link org.tonguetied.datatransfer.importing.Importer#createInstance(org.tonguetied.keywordmanagement.KeywordService, org.tonguetied.datatransfer.common.FormatType)}.
     */
    @Test
    public final void testCreateImporter() {
        Importer importer = ImporterFactory.getImporter(formatType, new KeywordServiceStub());
        assertEquals(expectedClass, importer.getClass());
        assertNotNull(importer.getKeywordService());
    }

}
