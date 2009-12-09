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

import org.tonguetied.datatransfer.common.ExportParameters;
import org.tonguetied.datatransfer.common.FormatType;
import org.tonguetied.keywordmanagement.Country;
import org.tonguetied.keywordmanagement.KeywordService;
import org.tonguetied.keywordmanagement.Country.CountryCode;


/**
 * Factory to create an {@link ExportDataPostProcessor}.
 * 
 * @author bsion
 *
 */
public class ExportDataPostProcessorFactory
{
    
    /**
     * Factory method to create the appropriate <code>ExportDataPostProcessor</code>.
     * 
     * @param formatType the input format of the data to process
     * @param parameters the parameters used to filter and format the data
     * @param keywordService the {@link KeywordService}
     * @return The newly created <code>ExportDataPostProcessor</code>
     */
    public static final ExportDataPostProcessor getPostProcessor(
            final FormatType formatType, 
            final ExportParameters parameters, 
            final KeywordService keywordService) 
    {
        ExportDataPostProcessor postProcessor = null;
        
        switch (formatType)
        {
            case properties:
            case resx:
                postProcessor = new ResourcePostProcessor(parameters);
                break;
            case xlsLanguage:
                final Country defaultCountry = 
                    keywordService.getCountry(CountryCode.DEFAULT);
                postProcessor = new LanguageCentricProcessor(
                        parameters, defaultCountry);
                break;
            default:
                postProcessor = null;
                break;
        }
        
        return postProcessor;
    }

}
