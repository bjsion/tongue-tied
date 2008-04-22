package org.tonguetied.datatransfer.importing;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.tonguetied.datatransfer.common.ImportParameters;
import org.tonguetied.datatransfer.importing.ImportException.ImportErrorCode;
import org.tonguetied.keywordmanagement.KeywordService;
import org.tonguetied.keywordmanagement.Translation.TranslationState;


/**
 * @author bsion
 *
 */
public class ImporterTest {
    private KeywordService keywordService;
    private Importer importer;
    private TranslationState expectedState;
    
    @Before
    public void setup() {
        // used for testing base validate method
        importer = new Importer(keywordService) {
            /* (non-Javadoc)
             * @see org.tonguetied.datatransfer.Importer#doImport(byte[])
             */
            @Override
            protected void doImport(byte[] input, TranslationState state) throws ImportException {
                assertNotNull(state);
                assertEquals(expectedState, state);
            }
        };
    }
    
    /**
     * Test method for {@link org.tonguetied.datatransfer.importing.Importer#importData(ImportParameters)}.
     */
    @Test
    public final void testImportDataWithNoData() {
        try {
            this.expectedState = TranslationState.VERIFIED;
            ImportParameters parameters = new ImportParameters();
            parameters.setData(new byte[] {});
            parameters.setTranslationState(expectedState);
            importer.importData(parameters);
            fail("should not have completed successfully");
        }
        catch (ImportException ie) {
            assertEquals(1, ie.getErrorCodes().size());
            assertEquals(ImportErrorCode.emptyData, ie.getErrorCodes().get(0));
        }
    }

    /**
     * Test method for {@link org.tonguetied.datatransfer.importing.Importer#importData(ImportParameters)}.
     */
    @Test
    public final void testImportDataWithNull() {
        try {
            this.expectedState = TranslationState.UNVERIFIED;
            ImportParameters parameters = new ImportParameters();
            parameters.setData(null);
            parameters.setTranslationState(expectedState);
            importer.importData(parameters);
            fail("should not have completed successfully");
        }
        catch (ImportException ie) {
            assertEquals(1, ie.getErrorCodes().size());
            assertEquals(ImportErrorCode.emptyData, ie.getErrorCodes().get(0));
        }
    }
    
    /**
     * Test method for {@link org.tonguetied.datatransfer.importing.Importer#importData(ImportParameters)}.
     */
    @Test
    public final void testImportDataWithNullState() {
        this.expectedState = TranslationState.UNVERIFIED;
        ImportParameters parameters = new ImportParameters();
        parameters.setData(new byte[] {0, 0, 0, 1});
        parameters.setTranslationState(null);
        importer.importData(parameters);
    }
}
