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
package org.tonguetied.datatransfer.importing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.tonguetied.datatransfer.importing.ImportException.ImportErrorCode;
import org.tonguetied.keywordmanagement.Country.CountryCode;
import org.tonguetied.keywordmanagement.Language.LanguageCode;
import org.tonguetied.keywordmanagement.Translation.TranslationState;

/**
 * Class contain unit tests for the {@link ImporterUtilsTest} class.
 * 
 * @author bsion
 *
 */
public class ImporterUtilsTest
{
    /**
     * Test method for {@link org.tonguetied.datatransfer.importing.ImporterUtils#evaluateLanguageCode(java.lang.String, java.util.List)}.
     */
    @Test
    public final void testEvaluateIllegalLanguageCode() throws Exception
    {
        List<ImportErrorCode> errorCodes = new ArrayList<ImportErrorCode>();
        LanguageCode code = ImporterUtils.evaluateLanguageCode("XX", errorCodes);
        assertEquals(1, errorCodes.size());
        assertTrue(errorCodes.contains(ImportErrorCode.illegalLanguage));
        assertNull(code);
    }

    /**
     * Test method for {@link org.tonguetied.datatransfer.importing.ImporterUtils#evaluateLanguageCode(java.lang.String, java.util.List)}.
     */
    @Test
    public final void testEvaluateNullLanguageCode() throws Exception
    {
        List<ImportErrorCode> errorCodes = new ArrayList<ImportErrorCode>();
        LanguageCode code = ImporterUtils.evaluateLanguageCode(null, errorCodes);
        assertEquals(1, errorCodes.size());
        assertTrue(errorCodes.contains(ImportErrorCode.illegalLanguage));
        assertNull(code);
    }

    /**
     * Test method for {@link org.tonguetied.datatransfer.importing.ImporterUtils#evaluateLanguageCode(java.lang.String, java.util.List)}.
     */
    @Test
    public final void testEvaluateLanguageCode()
    {
        List<ImportErrorCode> errorCodes = new ArrayList<ImportErrorCode>();
        LanguageCode code = ImporterUtils.evaluateLanguageCode("fy", errorCodes);
        assertEquals(LanguageCode.fy, code);
        assertTrue(errorCodes.isEmpty());
    }

    /**
     * Test method for {@link org.tonguetied.datatransfer.importing.ImporterUtils#evaluateCountryCode(java.lang.String, java.util.List)}.
     */
    @Test
    public final void testEvaluateIllegalCountryCode() throws Exception
    {
        List<ImportErrorCode> errorCodes = new ArrayList<ImportErrorCode>();
        CountryCode code = ImporterUtils.evaluateCountryCode("XX", errorCodes);
        assertEquals(1, errorCodes.size());
        assertTrue(errorCodes.contains(ImportErrorCode.illegalCountry));
        assertNull(code);
    }

    /**
     * Test method for {@link org.tonguetied.datatransfer.importing.ImporterUtils#evaluateCountryCode(java.lang.String, java.util.List)}.
     */
    @Test
    public final void testEvaluateNullCountryCode() throws Exception
    {
        List<ImportErrorCode> errorCodes = new ArrayList<ImportErrorCode>();
        CountryCode code = ImporterUtils.evaluateCountryCode(null, errorCodes);
        assertEquals(1, errorCodes.size());
        assertTrue(errorCodes.contains(ImportErrorCode.illegalCountry));
        assertNull(code);
    }

    /**
     * Test method for {@link org.tonguetied.datatransfer.importing.ImporterUtils#evaluateCountryCode(java.lang.String, java.util.List)}.
     */
    @Test
    public final void testEvaluateCountryCode()
    {
        List<ImportErrorCode> errorCodes = new ArrayList<ImportErrorCode>();
        CountryCode code = ImporterUtils.evaluateCountryCode("HR", errorCodes);
        assertEquals(CountryCode.HR, code);
        assertTrue(errorCodes.isEmpty());
    }

    /**
     * Test method for {@link org.tonguetied.datatransfer.importing.ImporterUtils#evaluateTranslationState(String, List)}.
     */
    @Test
    public final void testEvaluateTranslationState()
    {
        List<ImportErrorCode> errorCodes = new ArrayList<ImportErrorCode>();
        TranslationState state = ImporterUtils.evaluateTranslationState("UNVERIFIED", errorCodes);
        assertEquals(TranslationState.UNVERIFIED, state);
        assertTrue(errorCodes.isEmpty());
    }
    
    /**
     * Test method for {@link org.tonguetied.datatransfer.importing.ImporterUtils#evaluateTranslationState(String, List)}.
     */
    @Test
    public final void testEvaluateIllegalTranslationState()
    {
        List<ImportErrorCode> errorCodes = new ArrayList<ImportErrorCode>();
        TranslationState state = ImporterUtils.evaluateTranslationState("illegal", errorCodes);
        assertEquals(1, errorCodes.size());
        assertTrue(errorCodes.contains(ImportErrorCode.illegalTranslationState));
        assertNull(state);
    }

    /**
     * Test method for {@link org.tonguetied.datatransfer.importing.ImporterUtils#evaluateTranslationState(String, List)}.
     */
    @Test
    public final void testEvaluateNullTranslationState()
    {
        List<ImportErrorCode> errorCodes = new ArrayList<ImportErrorCode>();
        TranslationState state = ImporterUtils.evaluateTranslationState(null, errorCodes);
        assertEquals(1, errorCodes.size());
        assertTrue(errorCodes.contains(ImportErrorCode.illegalTranslationState));
        assertNull(state);
    }
}
