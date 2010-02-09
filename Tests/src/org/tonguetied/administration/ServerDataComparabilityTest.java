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
    
    private static final Date DATE_EQUALS = new Date(TICKS_EQUALS);
    private static final Date DATE_GREATER = new Date(TICKS_GREATER);
    private static final Date DATE_LESSER = new Date(TICKS_LESSER);

    /**
     * @param name
     */
    public ServerDataComparabilityTest(final String name)
    {
        super(name);
    }

    @Override
    protected Comparable<ServerData> createEqualInstance() throws Exception
    {
        ServerData serverData = new ServerData("1.1.0", "0012", DATE_EQUALS);
        serverData.setSetupDate(DATE_EQUALS);
        return serverData;
    }

    @Override
    protected Comparable<ServerData> createGreaterInstance() throws Exception
    {
        ServerData serverData =  new ServerData("1.10.0", "1024", DATE_GREATER);
        serverData.setSetupDate(DATE_EQUALS);
        return serverData;
    }

    @Override
    protected Comparable<ServerData> createLessInstance() throws Exception
    {
        ServerData serverData =  new ServerData("1.0.9", "5824", DATE_LESSER);
        serverData.setSetupDate(DATE_EQUALS);
        return serverData;
    }
}
