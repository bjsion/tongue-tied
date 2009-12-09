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
package org.tonguetied.datatransfer.exporting;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.Collection;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.tonguetied.datatransfer.common.ExportParameters;
import org.tonguetied.datatransfer.common.FormatType;
import org.tonguetied.keywordmanagement.Country;
import org.tonguetied.keywordmanagement.KeywordService;
import org.tonguetied.keywordmanagement.KeywordServiceStub;
import org.tonguetied.keywordmanagement.Country.CountryCode;

/**
 * Test class for {@link ExportDataPostProcessorFactory}.
 * 
 * @author bsion
 *
 */
@RunWith(value=Parameterized.class)
public class ExportDataPostProcessorFactoryTest
{
    
    private FormatType formatType;
    private Class<? extends ExportDataPostProcessor> expectedClass;
    
    private static KeywordService keywordService;
    private static ExportParameters parameters;

    @Parameters
    public static final Collection<Object[]> data()
    {
        return Arrays.asList(new Object[][] {
                {FormatType.xls, null},
                {FormatType.xlsLanguage, LanguageCentricProcessor.class},
                {FormatType.properties, ResourcePostProcessor.class},
                {FormatType.csv, null},
                {FormatType.resx, ResourcePostProcessor.class}
        });
    }

    @BeforeClass
    public final static void initialize()
    {
        keywordService = new KeywordServiceStub();
        Country country = new Country();
        country.setCode(CountryCode.DEFAULT);
        country.setName("default");
        keywordService.saveOrUpdate(country);
        
        parameters = new ExportParameters();
    }
    
    /**
     * Create a new instance of ExportDataPostProcessorFactoryTest.
     * 
     * @param formatType the {@link FormatType} to evaluate
     * @param expectedClass the expected class type that should be created
     */
    public ExportDataPostProcessorFactoryTest(final FormatType formatType,
            final Class<? extends ExportDataPostProcessor> expectedClass)
    {
        this.formatType = formatType;
        this.expectedClass = expectedClass;
    }

    /**
     * Test method for {@link org.tonguetied.datatransfer.exporting.ExportDataPostProcessorFactory#getPostProcessor(FormatType, ExportParameters, KeywordService)}.
     */
    @Test
    public final void testCreatePostProcessor()
    {
        ExportDataPostProcessor postProcessor = 
            ExportDataPostProcessorFactory.getPostProcessor(
                    formatType, parameters, keywordService);
        if (expectedClass == null)
        {
            assertNull(postProcessor);
        }
        else
        {
            assertEquals(expectedClass, postProcessor.getClass());
        }
    }

}
