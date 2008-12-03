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

import java.util.List;
import java.util.Map;

import org.tonguetied.datatransfer.common.ExportParameters;
import org.tonguetied.datatransfer.dao.TransferRepository;
import org.tonguetied.keywordmanagement.Translation;

/**
 * Data post processor that performs transformations / formatting of data
 * retrieved from persistence.
 * 
 * @author bsion
 * 
 */
public interface ExportDataPostProcessor
{

    /**
     * Using an existing list of {@link Translation}s, transform this list into
     * another list containing a different view of the same data in
     * <code>translations</code>.
     * 
     * @param translations the list of {@link Translation}s to transform
     * @param transferRepository interface to keyword dao
     * @return List of objects that may or may not be of type
     *         {@link Translation} that is a different representation of the
     *         <code>translations</code>
     */
    List<?> transformData(List<Translation> translations,
            TransferRepository transferRepository);

    /**
     * Add additional data used by the template to format the export data
     * 
     * @param root the root level object passed to the templating library
     * @param parameters the {@link ExportParameters} used to generate this
     *        export
     */
    void addData(Map<String, Object> root, ExportParameters parameters);
}
