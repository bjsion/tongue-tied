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

import java.io.Serializable;

import org.tonguetied.usermanagement.UserRight;
import org.tonguetied.usermanagement.UserRight.Permission;


import junitx.extensions.SerializabilityTestCase;

/**
 * @author bsion
 *
 */
public class UserRightSerializabilityTest extends SerializabilityTestCase {

    /**
     * @param name
     */
    public UserRightSerializabilityTest(String name) {
        super(name);
    }

    /* (non-Javadoc)
     * @see junitx.extensions.SerializabilityTestCase#createInstance()
     */
    @Override
    protected Serializable createInstance() throws Exception {
        UserRight userRight = new UserRight(Permission.ROLE_VERIFIER, null, null, null);
        return userRight;
    }

}
