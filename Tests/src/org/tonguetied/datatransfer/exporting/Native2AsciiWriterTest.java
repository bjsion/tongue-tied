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

import static org.junit.Assert.assertEquals;

import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.tonguetied.datatransfer.exporting.Native2AsciiDirective.Native2AsciiWriter;

/**
 * @author bsion
 *
 */
public class Native2AsciiWriterTest {
    
    private StringWriter out;
    private Native2AsciiWriter writer;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        out = new StringWriter();
        writer = new Native2AsciiWriter(out);
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
        IOUtils.closeQuietly(writer);
    }

    /**
     * Test method for {@link org.tonguetied.datatransfer.exporting.Native2AsciiDirective.Native2AsciiWriter#write(char[], int, int)}.
     */
    @Test
    public final void testWriteAsciiOnlyInput() throws Exception {
        String input = "abcd";
        char[] charArray = input.toCharArray();
        writer.write(charArray, 0, charArray.length);
        StringBuffer buffer = out.getBuffer();
        assertEquals(input, buffer.toString());
    }

    /**
     * Test method for {@link org.tonguetied.datatransfer.exporting.Native2AsciiDirective.Native2AsciiWriter#write(char[], int, int)}.
     */
    @Test
    public final void testWriteUnicodeOnlyInput() throws Exception {
        String input = "ùùéèçà";
        String expected = "\\u00f9\\u00f9\\u00e9\\u00e8\\u00e7\\u00e0";
        char[] charArray = input.toCharArray();
        writer.write(charArray, 0, charArray.length);
        StringBuffer buffer = out.getBuffer();
        assertEquals(expected, buffer.toString());
    }

    /**
     * Test method for {@link org.tonguetied.datatransfer.exporting.Native2AsciiDirective.Native2AsciiWriter#write(char[], int, int)}.
     */
    @Test
    public final void testWriteAsciiAndUnicodeInput() throws Exception {
        String input = "fromagè";
        String expected = "fromag\\u00e8";
        char[] charArray = input.toCharArray();
        writer.write(charArray, 0, charArray.length);
        StringBuffer buffer = out.getBuffer();
        assertEquals(expected, buffer.toString());
    }

}
