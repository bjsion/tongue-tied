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
        writer = new Native2AsciiWriter(out, false);
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
        final String input = "abcd";
        final char[] charArray = input.toCharArray();
        writer.write(charArray, 0, charArray.length);
        StringBuffer buffer = out.getBuffer();
        assertEquals(input, buffer.toString());
    }

    /**
     * Test method for {@link org.tonguetied.datatransfer.exporting.Native2AsciiDirective.Native2AsciiWriter#write(char[], int, int)}.
     */
    @Test
    public final void testWriteUnicodeOnlyInput() throws Exception
    {
        final String expected = "\\u00f9\\u00f9\\u00e9\\u00e8\\u00e7\\u00e0";
        final char[] charArray = {'ù','ù','é','è','ç','à'};
        writer.write(charArray, 0, charArray.length);
        StringBuffer buffer = out.getBuffer();
        assertEquals(expected, buffer.toString());
    }

    /**
     * Test method for {@link org.tonguetied.datatransfer.exporting.Native2AsciiDirective.Native2AsciiWriter#write(char[], int, int)}.
     */
    @Test
    public final void testWriteUnicode() throws Exception
    {
        final String expected = "\\u00c3\\u00a8\\u00c3\\u00a8";
        final char[] charArray = {'\u00c3','\u00a8','\u00c3','\u00a8'};
        writer.write(charArray, 0, charArray.length);
        StringBuffer buffer = out.getBuffer();
        assertEquals(expected, buffer.toString());
    }

    /**
     * Test method for {@link org.tonguetied.datatransfer.exporting.Native2AsciiDirective.Native2AsciiWriter#write(char[], int, int)}.
     * Octal should not be escaped
     */
    @Test
    public final void testWriteOctalOnlyInput() throws Exception
    {
        final String expected = "Jo>";
        final char[] charArray = {'\112', '\157', '\076'};
        writer.write(charArray, 0, charArray.length);
        StringBuffer buffer = out.getBuffer();
        assertEquals(expected, buffer.toString());
    }

    /**
     * Test method for {@link org.tonguetied.datatransfer.exporting.Native2AsciiDirective.Native2AsciiWriter#write(char[], int, int)}.
     */
    @Test
    public final void testWriteAsciiAndUnicodeInput() throws Exception
    {
        final String expected = "fromag\\u00e8";
        final char[] charArray = {'f','r','o','m','a','g','è'};
        writer.write(charArray, 0, charArray.length);
        StringBuffer buffer = out.getBuffer();
        assertEquals(expected, buffer.toString());
    }

    /**
     * Test method for {@link org.tonguetied.datatransfer.exporting.Native2AsciiDirective.Native2AsciiWriter#write(char[], int, int)}.
     */
    @Test
    public final void testWriteKey() throws Exception
    {
        final String expected = "some.key";
        final char[] charArray = {'s','o','m','e','.','k','e','y'};
        writer = new Native2AsciiWriter(out, true);
        writer.write(charArray, 0, charArray.length);
        StringBuffer buffer = out.getBuffer();
        assertEquals(expected, buffer.toString());
    }

    /**
     * Test method for {@link org.tonguetied.datatransfer.exporting.Native2AsciiDirective.Native2AsciiWriter#write(char[], int, int)}.
     */
    @Test
    public final void testWriteKeyWithSpaces() throws Exception
    {
        final String expected = "some\\ key";
        final char[] charArray = {'s','o','m','e',' ','k','e','y'};
        writer = new Native2AsciiWriter(out, true);
        writer.write(charArray, 0, charArray.length);
        StringBuffer buffer = out.getBuffer();
        assertEquals(expected, buffer.toString());
    }

    /**
     * Test method for {@link org.tonguetied.datatransfer.exporting.Native2AsciiDirective.Native2AsciiWriter#write(char[], int, int)}.
     */
    @Test
    public final void testWriteEscapeCharInput() throws Exception
    {
        final String expected = "\\n\\t \\r\\f\\\\";
        final char[] charArray = {'\n','\t',' ','\r','\f','\\'};
        writer.write(charArray, 0, charArray.length);
        StringBuffer buffer = out.getBuffer();
        assertEquals(expected, buffer.toString());
    }

    /**
     * Test method for {@link org.tonguetied.datatransfer.exporting.Native2AsciiDirective.Native2AsciiWriter#write(char[], int, int)}.
     */
    @Test
    public final void testWriteInvalidEscapeCharInput() throws Exception
    {
        final String expected = "b";
        final char[] charArray = {'\b'};
        writer.write(charArray, 0, charArray.length);
        StringBuffer buffer = out.getBuffer();
        assertEquals(expected, buffer.toString());
    }

//    /**
//     * Test method for {@link org.tonguetied.datatransfer.exporting.Native2AsciiDirective.Native2AsciiWriter#write(char[], int, int)}.
//     */
//    @Test
//    public final void testWriteIllegalEscapeCharInput() throws Exception
//    {
//        final String input = "\\z";
//        final String expected = "z";
//        char[] charArray = input.toCharArray();
//        writer.write(charArray, 0, charArray.length);
//        StringBuffer buffer = out.getBuffer();
//        assertEquals(expected, buffer.toString());
//    }
    
    /**
     * Test method for {@link org.tonguetied.datatransfer.exporting.Native2AsciiDirective.Native2AsciiWriter#write(char[], int, int)}.
     */
    @Test
    public final void testWriteIgnoredEscapeCharInput() throws Exception
    {
        final String expected = "\"'";
        char[] charArray = {'\"', '\''};
        writer.write(charArray, 0, charArray.length);
        StringBuffer buffer = out.getBuffer();
        assertEquals(expected, buffer.toString());
    }
}
