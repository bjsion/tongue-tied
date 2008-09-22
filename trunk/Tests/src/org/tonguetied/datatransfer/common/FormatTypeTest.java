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
package org.tonguetied.datatransfer.common;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import org.tonguetied.datatransfer.common.FormatType;

/**
 * Test class for the methods of the {@link FormatType} enum.
 * 
 * @author bsion
 *
 */
@RunWith(value=Parameterized.class)
public class FormatTypeTest {
    
    private FormatType formatType;
    private String expected;

    @Parameters
    public static final Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {FormatType.xls, "xls"},
                {FormatType.xlsLanguage, "xls"},
                {FormatType.properties, "properties"},
                {FormatType.csv, "csv"},
                {FormatType.resx, "resx"}
        });
    }

    /**
     * Create a new instance of FormatTypeTest.
     * 
     * @param formatType the {@link FormatType} to evaluate
     * @param expected the expected class type that should be created
     */
    public FormatTypeTest(FormatType formatType, String expected) {
        this.formatType = formatType;
        this.expected = expected;
    }

    /**
     * Test method for {@link FormatType#getDefaultFileExtension()}.
     */
    @Test
    public final void testCreateImporter() {
        String actual = formatType.getDefaultFileExtension();
        assertEquals(expected, actual);
    }

}
