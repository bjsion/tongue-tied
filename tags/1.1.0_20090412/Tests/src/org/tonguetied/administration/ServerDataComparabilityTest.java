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

import junitx.extensions.ComparabilityTestCase;

/**
 * Test class to ensure the {@link ServerData} class is compliant with the
 * <code>Comparable</code> interface.
 * 
 * @author bsion
 * 
 */
public class ServerDataComparabilityTest extends ComparabilityTestCase
{
    private static final long TICKS_EQUALS = new Date().getTime();
    private static final long TICKS_GREATER = new Date().getTime() + 999999999;
    private static final long TICKS_LESSER = new Date().getTime() - 999999999;

    /**
     * @param name
     */
    public ServerDataComparabilityTest(String name)
    {
        super(name);
    }

    @Override
    protected Comparable<ServerData> createEqualInstance() throws Exception
    {
        return new ServerData("1.0.1", "0012", new Date(TICKS_EQUALS));
    }

    @Override
    protected Comparable<ServerData> createGreaterInstance() throws Exception
    {
        return new ServerData("1.1.0", "1024", new Date(TICKS_GREATER));
    }

    @Override
    protected Comparable<ServerData> createLessInstance() throws Exception
    {
        return new ServerData("1.0.0", "5824", new Date(TICKS_LESSER));
    }
}
