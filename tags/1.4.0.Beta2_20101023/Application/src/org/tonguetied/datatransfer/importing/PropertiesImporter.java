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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.tonguetied.keywordmanagement.KeywordService;
import org.tonguetied.keywordmanagement.Translation;

/**
 * Data importer that handles input in the Java resource or property file 
 * format. The resource file is read and entries are transformed into 
 * {@link Translation}s and added to the system.
 *   
 * @author bsion
 * @see Properties
 */
public class PropertiesImporter extends AbstractPropertiesImporter
{
    /**
     * Create a new instance of PropertiesImporter.
     * 
     * @param keywordService the interface to keyword functions
     */
    public PropertiesImporter(KeywordService keywordService)
    {
        super(keywordService);
    }

    @Override
    protected Map<Object, Object> loadProperties(final byte[] input)
    {
        ByteArrayInputStream bais = null;
        try
        {
            // convert byte array into UTF-8 format rather than rely on the
            // default string encoding for this JVM
            final String inputString = new String(input, "UTF-8");
            bais = new ByteArrayInputStream(inputString.getBytes());
            Properties properties = new Properties();
            properties.load(bais);
            
            return properties;
        } 
        catch (IOException ioe)
        {
            throw new ImportException(ioe);
        }
        finally
        {
            IOUtils.closeQuietly(bais);
        }
    }
}
