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

import org.tonguetied.datatransfer.common.FormatType;
import org.tonguetied.keywordmanagement.KeywordService;

/**
 * Factory to create an {@link Importer}.
 * 
 * @author bsion
 *
 */
public class ImporterFactory {
    
    /**
     * Factory method to create the appropriate <code>Importer</code>.
     * 
     * @param formatType the input format of the data to process
     * @param keywordService interface to persistent storage
     * @return The newly created <code>Importer</code>
     */
    public static final Importer getImporter(
            FormatType formatType, KeywordService keywordService) 
    {
        Importer importer = null;
        
        switch (formatType) {
            case csv:
                importer = new CsvImporter(keywordService);
                break;
            case properties:
                importer = new PropertiesImporter(keywordService);
                break;
            case resx:
                importer = new ResourceImporter(keywordService);
                break;
            case xls:
            case xlsLanguage:
                importer = new ExcelImporter(keywordService);
                break;
        }
        
        return importer;
    }
}
