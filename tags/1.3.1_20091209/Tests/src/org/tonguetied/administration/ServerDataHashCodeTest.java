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
package org.tonguetied.administration;

import java.util.Date;

import junitx.extensions.EqualsHashCodeTestCase;


/**
 * Equals and hashcode method tests for {@link Object} {@link ServerData}
 * 
 * @author bsion
 *
 */
public class ServerDataHashCodeTest extends EqualsHashCodeTestCase
{
    private static final Date BUILD_DATE = new Date();

    public ServerDataHashCodeTest(String name)
    {
        super(name);
    }

    @Override
    protected Object createInstance() throws Exception
    {
        ServerData serverData = new ServerData("2.0.1", "5684", BUILD_DATE);

        return serverData;
    }

    @Override
    protected Object createNotEqualInstance() throws Exception
    {
        ServerData serverData = new ServerData("2.0.1", "6455", BUILD_DATE);

        return serverData;
    }
}
