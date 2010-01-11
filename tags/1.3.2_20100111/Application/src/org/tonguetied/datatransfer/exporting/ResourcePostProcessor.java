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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.tonguetied.datatransfer.common.ExportParameters;
import org.tonguetied.keywordmanagement.Bundle;
import org.tonguetied.keywordmanagement.Translation;
import org.tonguetied.keywordmanagement.TranslationKeywordPredicate;

/**
 * Performs data manipulation on {@link Translation}s for use in a resource file
 * demarcated by language and / or country exports.
 * 
 * @author bsion
 *
 */
public class ResourcePostProcessor implements ExportDataPostProcessor
{
    private ExportParameters parameters;
    
    private static final Logger logger = 
        Logger.getLogger(ResourcePostProcessor.class);

    /**
     * Create a new instance of the PropertiesPostProcessor.
     * 
     * @param parameters the parameters used to filter and format the data. 
     * Cannot be <code>null</code>
     */
    public ResourcePostProcessor(ExportParameters parameters)
    {
        if (parameters == null)
            throw new IllegalArgumentException("parameters cannot be null");
        
        this.parameters = parameters;
    }

    /**
     * Process the list of {@link Translation}s to include translations in 
     * global bundles into each selected bundle. If two translations exist with
     * different global bundles, then only one of the translations will be 
     * added. This will be process on first occurrence.
     * 
     * @param translations the {@link Translation}s to process
     * @return a processed list of {@link Translation}s
     */
    public List<Translation> transformData(List<Translation> translations)
    {
        List<Translation> results;
        if (parameters.isGlobalsMerged())
        {
            if (logger.isDebugEnabled())
                logger.debug("merging global bundle values");
            
            results = new ArrayList<Translation>();
            final List<Bundle> bundles = 
                getNonGlobalBundle(parameters.getBundles());
            Translation clone;
            TranslationKeywordPredicate predicate;
            for (Translation translation : translations)
            {
                if (translation.getBundle().isGlobal())
                {
                    for (final Bundle bundle : bundles)
                    {
                        predicate = new TranslationKeywordPredicate(
                                translation.getKeyword().getKeyword(),
                                bundle,
                                translation.getCountry(),
                                translation.getLanguage());
                        // If the translation for that keyword does not yet 
                        // exist then add
                        if (!CollectionUtils.exists(results, predicate))
                        {
                            clone = translation.deepClone();
                            clone.setBundle(bundle);
                            results.add(clone);
                        }
                    }
                }
                else
                {
                    results.add(translation);
                }
            }
        }
        else
        {
            if (translations == null)
                results = new ArrayList<Translation>();
            else
                results = translations;
        }
        
        return results;
    }

    public void addItems(Map<String, Object> root)
    {
        // Do nothing
    }

    /**
     * Find all the non global bundles from input bundle list.
     * 
     * @param bundles the list of bundles to process
     * @return a list of non-global bundles
     */
    private List<Bundle> getNonGlobalBundle(final List<Bundle> bundles)
    {
        List<Bundle> regulars = new ArrayList<Bundle>();
        
        for (final Bundle bundle : bundles)
        {
            if (!bundle.isGlobal())
                regulars.add(bundle);
        }
        
        return regulars;
    }
}
