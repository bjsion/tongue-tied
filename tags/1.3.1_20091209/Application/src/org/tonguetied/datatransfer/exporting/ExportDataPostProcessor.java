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

import org.tonguetied.keywordmanagement.Translation;

/**
 * Data post processor that performs transformations / formatting of data after
 * it has been retrieved from persistence.
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
     * @return List of objects that may or may not be of type
     *         {@link Translation} that is a different representation of the
     *         <code>translations</code>
     */
    List<?> transformData(List<Translation> translations);
    
    /**
     * If any data needs to be added specifically passed to the templating 
     * engine for this export type, then add it here.
     * 
     * @param root the data object
     */
    void addItems(Map<String, Object> root);
}
