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
package org.tonguetied.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Date;

import org.junit.Test;

/**
 * Unit tests for the {@link org.tonguetied.web.FileBean} class.
 * 
 * @author bsion
 *
 */
public class FileBeanTest
{

    /**
     * Test method for {@link org.tonguetied.web.FileBean#getLastModifiedDate()}.
     */
    @Test
    public final void testGetLastModifiedDate()
    {
        final FileBean file = new FileBean("test");
        final Date modifiedDate = file.getLastModifiedDate();
        assertEquals(new Date(0), modifiedDate);
    }

    /**
     * Test method for {@link org.tonguetied.web.FileBean#getFileType()}.
     */
    @Test
    public final void testGetFileTypeWithNoSuffix()
    {
        final FileBean file = new FileBean("test");
        final String fileType = file.getFileType();
        assertNull(fileType);
    }

    /**
     * Test method for {@link org.tonguetied.web.FileBean#getFileType()}.
     */
    @Test
    public final void testGetFileTypeWithEndDot()
    {
        final FileBean file = new FileBean("test.");
        final String fileType = file.getFileType();
        assertNull(fileType);
    }

    /**
     * Test method for {@link org.tonguetied.web.FileBean#getFileType()}.
     */
    @Test
    public final void testGetFileTypeWithUnknownSuffix()
    {
        final FileBean file = new FileBean("test.txt");
        final String fileType = file.getFileType();
        assertEquals("txt", fileType);
    }

    /**
     * Test method for {@link org.tonguetied.web.FileBean#getFileType()}.
     */
    @Test
    public final void testGetFileTypeWithValidSuffix()
    {
        final FileBean file = new FileBean("test.xls");
        final String fileType = file.getFileType();
        assertEquals("xls", fileType);
    }

    /**
     * Test method for {@link org.tonguetied.web.FileBean#getFileType()}.
     */
    @Test
    public final void testGetFileTypeWithZip()
    {
        final FileBean file = new FileBean("test.zip");
        final String fileType = file.getFileType();
        assertEquals("zip", fileType);
    }
}
