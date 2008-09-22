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
package org.tonguetied.usermanagement;

import org.tonguetied.usermanagement.UserRight;
import org.tonguetied.usermanagement.UserRight.Permission;

import junitx.extensions.ComparabilityTestCase;

/**
 * Test class to ensure the {@link UserRight} class is compliant with the 
 * <code>Comparable</code> interface.
 * 
 * @author bsion
 *
 */
public class UserRightComparabilityTest extends ComparabilityTestCase
{
    
    /**
     * @param name
     */
    public UserRightComparabilityTest(String name)
    {
        super(name);
    }

    /* (non-Javadoc)
     * @see junitx.extensions.ComparabilityTestCase#createEqualInstance()
     */
    @Override
    protected Comparable<UserRight> createEqualInstance() throws Exception
    {
        UserRight userRight = new UserRight(Permission.ROLE_USER, null, null, null);
        return userRight;
    }

    /* (non-Javadoc)
     * @see junitx.extensions.ComparabilityTestCase#createGreaterInstance()
     */
    @Override
    protected Comparable<UserRight> createGreaterInstance() throws Exception
    {
        UserRight userRight = new UserRight(Permission.ROLE_VERIFIER, null, null, null);
        return userRight;
    }

    /* (non-Javadoc)
     * @see junitx.extensions.ComparabilityTestCase#createLessInstance()
     */
    @Override
    protected Comparable<UserRight> createLessInstance() throws Exception
    {
        UserRight userRight = new UserRight(Permission.ROLE_ADMIN, null, null, null);
        return userRight;
    }

}
