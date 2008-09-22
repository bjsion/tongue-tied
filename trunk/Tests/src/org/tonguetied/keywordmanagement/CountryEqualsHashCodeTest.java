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

import org.tonguetied.keywordmanagement.Country;
import org.tonguetied.keywordmanagement.Country.CountryCode;

import junitx.extensions.EqualsHashCodeTestCase;


/**
 * @author bsion
 *
 */
public class CountryEqualsHashCodeTest extends EqualsHashCodeTestCase {

    public CountryEqualsHashCodeTest(String name) {
        super(name);
    }

    @Override
    protected Object createInstance() throws Exception {
        Country country = new Country();
        country.setCode(CountryCode.SG);
        country.setName("Singapore");
        
        return country;
    }

    @Override
    protected Object createNotEqualInstance() throws Exception {
        Country country = new Country();
        country.setCode(CountryCode.SG);
        country.setName("Singapura");
        
        return country;
    }
}
