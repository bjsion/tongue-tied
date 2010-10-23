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

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.tonguetied.datatransfer.importing.ImportException.ImportErrorCode;
import org.tonguetied.keywordmanagement.KeywordService;
import org.tonguetied.keywordmanagement.Translation;

/**
 * Data importer that handles input in the Java FX property file 
 * format. The resource file is read and entries are transformed into 
 * {@link Translation}s and added to the system.
 *   
 * @author bsion
 * @see "http://download.oracle.com/docs/cd/E17802_01/javafx/javafx/1.3/docs/api/javafx.util/javafx.util.Properties.html"
 */
public class JavaFxPropertiesImporter extends AbstractPropertiesImporter
{
    /**
     * Create a new instance of PropertiesImporter.
     * 
     * @param keywordService the interface to keyword functions
     */
    public JavaFxPropertiesImporter(KeywordService keywordService)
    {
        super(keywordService);
    }

    @Override
    protected Map<Object, Object> loadProperties(final byte[] input)
    {
        // convert byte array into UTF-8 format rather than rely on the
        // default string encoding for this JVM
        String inputString;
        try
        {
            inputString = new String(input, "UTF-8");
            Map<Object, Object> properties = new HashMap<Object, Object>();
            final String[] lines = inputString.split("\n");
            String[] keyValue;
            List<ImportErrorCode> errorCodes = new ArrayList<ImportErrorCode>();
            for (final String line : lines)
            {
                // Ignore comments
                if (!line.startsWith("//") && !line.startsWith("@charset"))
                {
                    keyValue = line.split("=");
                    properties.put(extract(keyValue[0], errorCodes), 
                            StringEscapeUtils.unescapeJava(
                                    extract(keyValue[1], errorCodes)));
                }
            }
            
            return properties;
        }
        catch (UnsupportedEncodingException uee)
        {
            throw new ImportException(uee);
        }
    }

    /**
     * @param string
     * @return
     */
    private String extract(final String string, List<ImportErrorCode> errorCodes)
    {
        final String trimmed = string.trim();
        String result = null;
        if (trimmed.startsWith("\"") && trimmed.endsWith("\""))
        {
            result = trimmed.substring(1, trimmed.length()-1);
        }
        else
        {
            errorCodes.add(ImportErrorCode.invalidKeyValueFormat);
        }
        return result;
    }
}
