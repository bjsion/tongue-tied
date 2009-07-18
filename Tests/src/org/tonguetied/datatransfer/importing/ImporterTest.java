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

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.tonguetied.datatransfer.common.ImportParameters;
import org.tonguetied.datatransfer.importing.ImportException.ImportErrorCode;
import org.tonguetied.keywordmanagement.Translation.TranslationState;


/**
 * @author bsion
 *
 */
public class ImporterTest
{
    private Importer importer;
    private TranslationState expectedState;
    
    @Before
    public void setUp()
    {
        // used for testing base validate method
        importer = new Importer(null)
        {
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
    public final void testImportDataWithNoData()
    {
        try
        {
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
    public final void testImportDataWithNull()
    {
        try
        {
            this.expectedState = TranslationState.UNVERIFIED;
            ImportParameters parameters = new ImportParameters();
            parameters.setData(null);
            parameters.setTranslationState(expectedState);
            importer.importData(parameters);
            fail("should not have completed successfully");
        }
        catch (ImportException ie)
        {
            assertEquals(1, ie.getErrorCodes().size());
            assertEquals(ImportErrorCode.emptyData, ie.getErrorCodes().get(0));
        }
    }
    
    /**
     * Test method for {@link org.tonguetied.datatransfer.importing.Importer#importData(ImportParameters)}.
     */
    @Test
    public final void testImportDataWithNullState()
    {
        this.expectedState = TranslationState.UNVERIFIED;
        ImportParameters parameters = new ImportParameters();
        parameters.setData(new byte[] {0, 0, 0, 1});
        parameters.setTranslationState(null);
        importer.importData(parameters);
    }
}
