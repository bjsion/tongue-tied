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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.tonguetied.datatransfer.common.FormatType;
import org.tonguetied.keywordmanagement.KeywordServiceStub;

/**
 * Unit tests for class {@link ImporterFactory}.
 * 
 * @author bsion
 *
 */
@RunWith(value=Parameterized.class)
public class ImporterFactoryTest
{
    
    private FormatType formatType;
    private Class<? extends Importer> expectedClass;

    @Parameters
    public static final Collection<Object[]> data()
    {
        return Arrays.asList(new Object[][] {
                {FormatType.xls, ExcelImporter.class},
                {FormatType.xlsLanguage, ExcelImporter.class},
                {FormatType.properties, PropertiesImporter.class},
                {FormatType.csv, CsvImporter.class},
                {FormatType.resx, ResourceImporter.class},
                {null, null}
        });
    }

    /**
     * Create a new instance of ExportDataPostProcessorFactoryTest.
     * 
     * @param formatType the {@link FormatType} to evaluate
     * @param expectedClass the expected class type that should be created
     */
    public ImporterFactoryTest(FormatType formatType,
            Class<? extends Importer> expectedClass)
    {
        this.formatType = formatType;
        this.expectedClass = expectedClass;
    }

    /**
     * Test method for {@link ImporterFactory#getImporter(FormatType, org.tonguetied.keywordmanagement.KeywordService, org.tonguetied.datatransfer.dao.TransferRepository)}.
     */
    @Test
    public final void testCreateImporter()
    {
        if (formatType != null)
        {
            final Importer importer = ImporterFactory.getImporter(
                    formatType, new KeywordServiceStub(), null);
            assertEquals(expectedClass, importer.getClass());
            assertNotNull(importer.getKeywordService());
        }
    }

    /**
     * Test method for {@link ImporterFactory#getImporter(FormatType, org.tonguetied.keywordmanagement.KeywordService, org.tonguetied.datatransfer.dao.TransferRepository)}.
     */
    @Test(expected=IllegalArgumentException.class)
    public final void testCreateImporterWithNull()
    {
        if (formatType == null)
        {
            ImporterFactory.getImporter(
                    formatType, new KeywordServiceStub(), null);
            fail("should not reach here");
        }
        else
        {
            throw new IllegalArgumentException();
        }
    }
}
