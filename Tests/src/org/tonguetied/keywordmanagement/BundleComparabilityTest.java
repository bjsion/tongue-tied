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
package org.tonguetied.keywordmanagement;

import org.tonguetied.keywordmanagement.Bundle;

import junitx.extensions.ComparabilityTestCase;

/**
 * Test class to ensure the {@link Bundle} class is compliant with the 
 * <code>Comparable</code> interface.
 * 
 * @author bsion
 *
 */
public class BundleComparabilityTest extends ComparabilityTestCase
{
    
    /**
     * @param name
     */
    public BundleComparabilityTest(String name) {
        super(name);
    }

    /* (non-Javadoc)
     * @see junitx.extensions.ComparabilityTestCase#createEqualInstance()
     */
    @Override
    protected Comparable<Bundle> createEqualInstance() throws Exception {
        Bundle bundle = new Bundle();
        bundle.setName("bundle");
        bundle.setDescription("description");
        bundle.setResourceName("bbb");
        bundle.setDefault(false);
        return bundle;
    }

    /* (non-Javadoc)
     * @see junitx.extensions.ComparabilityTestCase#createGreaterInstance()
     */
    @Override
    protected Comparable<Bundle> createGreaterInstance() throws Exception {
        Bundle bundle = new Bundle();
        bundle.setName("bundle");
        bundle.setDescription("description");
        bundle.setResourceName("ccc");
        bundle.setDefault(false);
        return bundle;
    }

    /* (non-Javadoc)
     * @see junitx.extensions.ComparabilityTestCase#createLessInstance()
     */
    @Override
    protected Comparable<Bundle> createLessInstance() throws Exception {
        Bundle bundle = new Bundle();
        bundle.setName("bundle");
        bundle.setDescription("description");
        bundle.setResourceName("aaa");
        bundle.setDefault(false);
        return bundle;
    }

}
