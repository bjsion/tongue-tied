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

/**
 * Exception used to indicate that export failed because no matching data can
 * be found that matches the export parameters.
 * 
 * @author bsion
 *
 */
public class NoExportDataException extends ExportException
{
    private static final long serialVersionUID = -1692991938913153166L;
    
    private ExportParameters parameters;

    /**
     * Create a new instance of NoExportDataException.
     *
     * @param parameters the export parameters used to create the export.
     */
    public NoExportDataException(final ExportParameters parameters)
    {
        super("no keywords found for export criteria ");
    }

    /**
     * @return the export parameters that were executed when this exception was
     * generated.
     */
    public ExportParameters getParameters()
    {
        return parameters;
    }
}
