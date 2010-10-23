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

import static org.tonguetied.datatransfer.common.FormatType.javafx;
import static org.tonguetied.datatransfer.importing.Constants.TEST_DATA_DIR;
import static org.tonguetied.keywordmanagement.Bundle.TABLE_BUNDLE;
import static org.tonguetied.keywordmanagement.Country.TABLE_COUNTRY;
import static org.tonguetied.keywordmanagement.Keyword.TABLE_KEYWORD;
import static org.tonguetied.keywordmanagement.Language.TABLE_LANGUAGE;
import static org.tonguetied.keywordmanagement.Translation.TABLE_TRANSLATION;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

import org.apache.commons.io.FileUtils;
import org.tonguetied.datatransfer.common.ImportParameters;
import org.tonguetied.datatransfer.dao.TransferRepository;
import org.tonguetied.datatransfer.dao.TransferRepositoryStub;
import org.tonguetied.datatransfer.importing.ImportException.ImportErrorCode;
import org.tonguetied.keywordmanagement.Bundle;
import org.tonguetied.keywordmanagement.Country;
import org.tonguetied.keywordmanagement.Keyword;
import org.tonguetied.keywordmanagement.KeywordService;
import org.tonguetied.keywordmanagement.Language;
import org.tonguetied.keywordmanagement.Translation;
import org.tonguetied.keywordmanagement.Country.CountryCode;
import org.tonguetied.keywordmanagement.Language.LanguageCode;
import org.tonguetied.keywordmanagement.Translation.TranslationState;
import org.tonguetied.test.common.AbstractServiceTest;

/**
 * Class contain unit tests for the {@link JavaFxPropertiesImporter} class.
 * 
 * @author bsion
 * 
 */
public final class JavaFxPropertiesImporterTest extends AbstractServiceTest
{

    private KeywordService keywordService;
    private TransferRepository transferRepository = new TransferRepositoryStub();
    private Language defaultLanguage;
    private Language hebrew;
    private Language arabic;
    private Country defaultCountry;
    private Country israel;
    private Country kuwait;
    private Country yemen;
    private Bundle bundle1;
    private Bundle bundle2;

    @Override
    protected void onSetUpInTransaction() throws Exception
    {
        defaultLanguage = new Language();
        defaultLanguage.setCode(LanguageCode.DEFAULT);
        defaultLanguage.setName("Default");

        hebrew = new Language();
        hebrew.setCode(LanguageCode.he);
        hebrew.setName("Hebrew");

        arabic = new Language();
        arabic.setCode(LanguageCode.ar);
        arabic.setName("Arabic");

        defaultCountry = new Country();
        defaultCountry.setCode(CountryCode.DEFAULT);
        defaultCountry.setName("Default");

        israel = new Country();
        israel.setCode(CountryCode.IL);
        israel.setName("Israel");

        yemen = new Country();
        yemen.setCode(CountryCode.YE);
        yemen.setName("Yemen");

        kuwait = new Country();
        kuwait.setCode(CountryCode.KW);
        kuwait.setName("Kuwait");

        bundle1 = new Bundle();
        bundle1.setName("bundle1");
        bundle1.setResourceName("JavaFxImporterTest");
        bundle1.setGlobal(false);

        bundle2 = new Bundle();
        bundle2.setName("bundle2");
        bundle2.setResourceName("/test/bundle2");
        bundle2.setGlobal(false);

        keywordService.saveOrUpdate(defaultLanguage);
        keywordService.saveOrUpdate(hebrew);
        keywordService.saveOrUpdate(arabic);
        keywordService.saveOrUpdate(bundle1);
        keywordService.saveOrUpdate(bundle2);
        keywordService.saveOrUpdate(defaultCountry);
        keywordService.saveOrUpdate(israel);
        keywordService.saveOrUpdate(kuwait);
        keywordService.saveOrUpdate(yemen);

        Keyword keywordThree = new Keyword();
        keywordThree.setKeyword("import.test.three");
        Translation translationThree_1 = new Translation(bundle1,
                defaultCountry, defaultLanguage, "old",
                TranslationState.QUERIED);
        keywordThree.addTranslation(translationThree_1);

        Keyword keywordFour = new Keyword();
        keywordFour.setKeyword("import.test.four");
        Translation translationFour_1 = new Translation(bundle1,
                defaultCountry, defaultLanguage, "old",
                TranslationState.QUERIED);
        keywordFour.addTranslation(translationFour_1);

        Keyword keywordFive = new Keyword();
        keywordFive.setKeyword("import.test.five");
        Translation translationFive_1 = new Translation(bundle1,
                defaultCountry, arabic, "old", TranslationState.QUERIED);
        keywordFive.addTranslation(translationFive_1);

        keywordService.saveOrUpdate(keywordThree);
        keywordService.saveOrUpdate(keywordFour);
        keywordService.saveOrUpdate(keywordFive);
    }

    /**
     * Test method for
     * {@link JavaFxPropertiesImporter#importData(ImportParameters)}.
     */
    public final void testImportData() throws Exception
    {
        final String fileName = "JavaFxImporterTest";
        File file = new File(TEST_DATA_DIR, fileName + "."
                + javafx.getDefaultFileExtension());
        byte[] input = FileUtils.readFileToByteArray(file);
        final Importer importer = ImporterFactory.getImporter(javafx,
                keywordService, transferRepository);
        TranslationState expectedState = TranslationState.VERIFIED;
        ImportParameters parameters = new ImportParameters();
        parameters.setData(input);
        parameters.setTranslationState(expectedState);
        parameters.setFileName(fileName);
        importer.importData(parameters);
        Keyword actual = keywordService.getKeyword("import.test.one");
        assertNotNull(actual);
        SortedSet<Translation> translations = actual.getTranslations();
        assertEquals(1, translations.size());
        Object[] actualTranslations = actual.getTranslations().toArray();
        Translation actualTranslation = (Translation) actualTranslations[0];
        assertEquals(defaultCountry, actualTranslation.getCountry());
        assertEquals(defaultLanguage, actualTranslation.getLanguage());
        assertEquals(bundle1, actualTranslation.getBundle());
        assertEquals(expectedState, actualTranslation.getState());
        assertEquals("Value 1", actualTranslation.getValue());

        actual = keywordService.getKeyword("import.test.two");
        assertNotNull(actual);
        translations = actual.getTranslations();
        assertEquals(1, translations.size());
        actualTranslations = actual.getTranslations().toArray();
        actualTranslation = (Translation) actualTranslations[0];
        assertEquals(defaultCountry, actualTranslation.getCountry());
        assertEquals(defaultLanguage, actualTranslation.getLanguage());
        assertEquals(bundle1, actualTranslation.getBundle());
        assertEquals(expectedState, actualTranslation.getState());
        assertNull(actualTranslation.getValue());

        actual = keywordService.getKeyword("import.test.three");
        assertNotNull(actual);
        translations = actual.getTranslations();
        assertEquals(1, translations.size());
        actualTranslations = actual.getTranslations().toArray();
        actualTranslation = (Translation) actualTranslations[0];
        assertEquals(defaultCountry, actualTranslation.getCountry());
        assertEquals(defaultLanguage, actualTranslation.getLanguage());
        assertEquals(bundle1, actualTranslation.getBundle());
        assertEquals(expectedState, actualTranslation.getState());
        assertEquals("new value 3", actualTranslation.getValue());

        actual = keywordService.getKeyword("import.test.four");
        assertNotNull(actual);
        translations = actual.getTranslations();
        assertEquals(1, translations.size());
        actualTranslations = actual.getTranslations().toArray();
        actualTranslation = (Translation) actualTranslations[0];
        assertEquals(defaultCountry, actualTranslation.getCountry());
        assertEquals(defaultLanguage, actualTranslation.getLanguage());
        assertEquals(bundle1, actualTranslation.getBundle());
        assertEquals(expectedState, actualTranslation.getState());
        assertNull(actualTranslation.getValue());

        actual = keywordService.getKeyword("import.test.five");
        assertNotNull(actual);
        translations = actual.getTranslations();
        assertEquals(2, translations.size());
        actualTranslations = actual.getTranslations().toArray();
        actualTranslation = (Translation) actualTranslations[0];
        assertEquals(defaultCountry, actualTranslation.getCountry());
        assertEquals(defaultLanguage, actualTranslation.getLanguage());
        assertEquals(bundle1, actualTranslation.getBundle());
        assertEquals(expectedState, actualTranslation.getState());
        assertEquals("value 5 \nnew line", actualTranslation.getValue());

        actual = keywordService.getKeyword("import.test.six");
        assertNotNull(actual);
        translations = actual.getTranslations();
        assertEquals(1, translations.size());
        actualTranslations = actual.getTranslations().toArray();
        actualTranslation = (Translation) actualTranslations[0];
        assertEquals(defaultCountry, actualTranslation.getCountry());
        assertEquals(defaultLanguage, actualTranslation.getLanguage());
        assertEquals(bundle1, actualTranslation.getBundle());
        assertEquals(expectedState, actualTranslation.getState());
        assertEquals("你好", actualTranslation.getValue());

        actual = keywordService.getKeyword("import test seven");
        assertNotNull(actual);
        translations = actual.getTranslations();
        assertEquals(1, translations.size());
        actualTranslations = actual.getTranslations().toArray();
        actualTranslation = (Translation) actualTranslations[0];
        assertEquals(defaultCountry, actualTranslation.getCountry());
        assertEquals(defaultLanguage, actualTranslation.getLanguage());
        assertEquals(bundle1, actualTranslation.getBundle());
        assertEquals(expectedState, actualTranslation.getState());
        assertEquals("שלום", actualTranslation.getValue());
    }

    /**
     * Test method for
     * {@link JavaFxPropertiesImporter#importData(ImportParameters)}.
     */
    public final void testImportDataWithBundle() throws Exception
    {
        final String fileName = "JavaFxImporterTest";
        File file = new File(TEST_DATA_DIR, fileName + "."
                + javafx.getDefaultFileExtension());
        byte[] input = FileUtils.readFileToByteArray(file);
        final Importer importer = ImporterFactory.getImporter(javafx,
                keywordService, transferRepository);
        TranslationState expectedState = TranslationState.VERIFIED;
        ImportParameters parameters = new ImportParameters();
        parameters.setData(input);
        parameters.setTranslationState(expectedState);
        parameters.setFileName(fileName);
        parameters.setBundle(bundle2);
        importer.importData(parameters);
        Keyword actual = keywordService.getKeyword("import.test.one");
        assertNotNull(actual);
        SortedSet<Translation> translations = actual.getTranslations();
        assertEquals(1, translations.size());
        Object[] actualTranslations = actual.getTranslations().toArray();
        Translation actualTranslation = (Translation) actualTranslations[0];
        assertEquals(defaultCountry, actualTranslation.getCountry());
        assertEquals(defaultLanguage, actualTranslation.getLanguage());
        assertEquals(bundle2, actualTranslation.getBundle());
        assertEquals(expectedState, actualTranslation.getState());
        assertEquals("Value 1", actualTranslation.getValue());

        actual = keywordService.getKeyword("import.test.two");
        assertNotNull(actual);
        translations = actual.getTranslations();
        assertEquals(1, translations.size());
        actualTranslations = actual.getTranslations().toArray();
        actualTranslation = (Translation) actualTranslations[0];
        assertEquals(defaultCountry, actualTranslation.getCountry());
        assertEquals(defaultLanguage, actualTranslation.getLanguage());
        assertEquals(bundle2, actualTranslation.getBundle());
        assertEquals(expectedState, actualTranslation.getState());
        assertNull(actualTranslation.getValue());

        actual = keywordService.getKeyword("import.test.three");
        assertNotNull(actual);
        translations = actual.getTranslations();
        assertEquals(2, translations.size());
        actualTranslations = actual.getTranslations().toArray();
        actualTranslation = (Translation) actualTranslations[1];
        assertEquals(defaultCountry, actualTranslation.getCountry());
        assertEquals(defaultLanguage, actualTranslation.getLanguage());
        assertEquals(bundle2, actualTranslation.getBundle());
        assertEquals(expectedState, actualTranslation.getState());
        assertEquals("new value 3", actualTranslation.getValue());

        actual = keywordService.getKeyword("import.test.four");
        assertNotNull(actual);
        translations = actual.getTranslations();
        assertEquals(2, translations.size());
        actualTranslations = actual.getTranslations().toArray();
        actualTranslation = (Translation) actualTranslations[1];
        assertEquals(defaultCountry, actualTranslation.getCountry());
        assertEquals(defaultLanguage, actualTranslation.getLanguage());
        assertEquals(bundle2, actualTranslation.getBundle());
        assertEquals(expectedState, actualTranslation.getState());
        assertNull(actualTranslation.getValue());

        actual = keywordService.getKeyword("import.test.five");
        assertNotNull(actual);
        translations = actual.getTranslations();
        assertEquals(2, translations.size());
        actualTranslations = actual.getTranslations().toArray();
        actualTranslation = (Translation) actualTranslations[0];
        assertEquals(defaultCountry, actualTranslation.getCountry());
        assertEquals(defaultLanguage, actualTranslation.getLanguage());
        assertEquals(bundle2, actualTranslation.getBundle());
        assertEquals(expectedState, actualTranslation.getState());
        assertEquals("value 5 \nnew line", actualTranslation.getValue());

        actual = keywordService.getKeyword("import.test.six");
        assertNotNull(actual);
        translations = actual.getTranslations();
        assertEquals(1, translations.size());
        actualTranslations = actual.getTranslations().toArray();
        actualTranslation = (Translation) actualTranslations[0];
        assertEquals(defaultCountry, actualTranslation.getCountry());
        assertEquals(defaultLanguage, actualTranslation.getLanguage());
        assertEquals(bundle2, actualTranslation.getBundle());
        assertEquals(expectedState, actualTranslation.getState());
        assertEquals("你好", actualTranslation.getValue());

        actual = keywordService.getKeyword("import test seven");
        assertNotNull(actual);
        translations = actual.getTranslations();
        assertEquals(1, translations.size());
        actualTranslations = actual.getTranslations().toArray();
        actualTranslation = (Translation) actualTranslations[0];
        assertEquals(defaultCountry, actualTranslation.getCountry());
        assertEquals(defaultLanguage, actualTranslation.getLanguage());
        assertEquals(bundle2, actualTranslation.getBundle());
        assertEquals(expectedState, actualTranslation.getState());
        assertEquals("שלום", actualTranslation.getValue());
    }

//    /**
//     * Test method for
//     * {@link JavaFxPropertiesImporter#importData(ImportParameters)}.
//     */
//    public final void testImportWithIllegalValues() throws Exception
//    {
//        final String fileName = "JavaFxImporterTestWithIllegalValues";
//        File file = new File(TEST_DATA_DIR, fileName + "."
//                + javafx.getDefaultFileExtension());
//        byte[] input = FileUtils.readFileToByteArray(file);
//        final Importer importer = ImporterFactory.getImporter(javafx,
//                keywordService, transferRepository);
//        TranslationState expectedState = TranslationState.VERIFIED;
//        ImportParameters parameters = new ImportParameters();
//        parameters.setData(input);
//        parameters.setTranslationState(expectedState);
//        parameters.setBundle(bundle1);
//        parameters.setFileName(fileName);
//        try 
//        {
//            importer.importData(parameters);
//            fail();
//        }
//        catch (ImportException ie)
//        {
//            List<ImportErrorCode> errorCodes = ie.getErrorCodes();
//            assertEquals(1, errorCodes.size());
//            assertTrue(errorCodes.contains(ImportErrorCode.invalidKeyValueFormat));
//        }
//        Keyword actual = keywordService.getKeyword("import.test.one");
//        assertNotNull(actual);
//        SortedSet<Translation> translations = actual.getTranslations();
//        assertEquals(1, translations.size());
//        Object[] actualTranslations = actual.getTranslations().toArray();
//        Translation actualTranslation = (Translation) actualTranslations[0];
//        assertEquals(defaultCountry, actualTranslation.getCountry());
//        assertEquals(defaultLanguage, actualTranslation.getLanguage());
//        assertEquals(bundle1, actualTranslation.getBundle());
//        assertEquals(TranslationState.UNVERIFIED, actualTranslation.getState());
//        assertEquals("Value 1", actualTranslation.getValue());
//
//        actual = keywordService.getKeyword("import.test.two");
//        assertNull(actual);
//        actual = keywordService.getKeyword("import.test.six");
//        assertNull(actual);
//    }

    /**
     * Test method for
     * {@link JavaFxPropertiesImporter#validate(String, Bundle, List)}.
     */
    public final void testValidateWithIllegalCountry() throws Exception
    {
        final String fileName = "JavaFxImporterTest_XX";
        List<ImportErrorCode> errorCodes = new ArrayList<ImportErrorCode>();
        final Importer importer = ImporterFactory.getImporter(javafx,
                keywordService, transferRepository);
        importer.validate(fileName, null, errorCodes);
        assertEquals(2, errorCodes.size());
        assertTrue(errorCodes.contains(ImportErrorCode.illegalCountry));
        assertTrue(errorCodes.contains(ImportErrorCode.unknownCountry));
    }

    /**
     * Test method for
     * {@link JavaFxPropertiesImporter#validate(String, Bundle, List)}.
     */
    public final void testValidateWithUnknownCountry() throws Exception
    {
        final String fileName = "JavaFxImporterTest_AL";
        List<ImportErrorCode> errorCodes = new ArrayList<ImportErrorCode>();
        final Importer importer = ImporterFactory.getImporter(javafx,
                keywordService, transferRepository);
        importer.validate(fileName, null, errorCodes);
        assertEquals(1, errorCodes.size());
        assertEquals(ImportErrorCode.unknownCountry, errorCodes.get(0));
    }

    /**
     * Test method for
     * {@link JavaFxPropertiesImporter#validate(String, Bundle, List)}.
     */
    public final void testValidateWithKnownCountry() throws Exception
    {
        final String fileName = "JavaFxImporterTest_IL";
        List<ImportErrorCode> errorCodes = new ArrayList<ImportErrorCode>();
        final JavaFxPropertiesImporter importer = (JavaFxPropertiesImporter) ImporterFactory
                .getImporter(javafx, keywordService, transferRepository);
        importer.validate(fileName, null, errorCodes);
        assertTrue(errorCodes.isEmpty());
        assertEquals(bundle1, importer.getBundle());
        assertEquals(israel, importer.getCountry());
        assertEquals(defaultLanguage, importer.getLanguage());
    }

    /**
     * Test method for
     * {@link JavaFxPropertiesImporter#validate(String, Bundle, List)}.
     */
    public final void testValidateWithIllegalLanguage() throws Exception
    {
        final String fileName = "JavaFxImporterTest_xx";
        List<ImportErrorCode> errorCodes = new ArrayList<ImportErrorCode>();
        final Importer importer = ImporterFactory.getImporter(javafx,
                keywordService, transferRepository);
        importer.validate(fileName, null, errorCodes);
        assertEquals(2, errorCodes.size());
        assertTrue(errorCodes.contains(ImportErrorCode.illegalLanguage));
        assertTrue(errorCodes.contains(ImportErrorCode.unknownLanguage));
    }

    /**
     * Test method for
     * {@link JavaFxPropertiesImporter#validate(String, Bundle, List)}.
     */
    public final void testValidateWithUnknownLanguage() throws Exception
    {
        final String fileName = "JavaFxImporterTest_es";
        List<ImportErrorCode> errorCodes = new ArrayList<ImportErrorCode>();
        final Importer importer = ImporterFactory.getImporter(javafx,
                keywordService, transferRepository);
        importer.validate(fileName, null, errorCodes);
        assertEquals(1, errorCodes.size());
        assertEquals(ImportErrorCode.unknownLanguage, errorCodes.get(0));
    }

    /**
     * Test method for
     * {@link JavaFxPropertiesImporter#validate(String, Bundle, List)}.
     */
    public final void testValidateWithKnownLanguage() throws Exception
    {
        final String fileName = "JavaFxImporterTest_he";
        List<ImportErrorCode> errorCodes = new ArrayList<ImportErrorCode>();
        final JavaFxPropertiesImporter importer = (JavaFxPropertiesImporter) ImporterFactory
                .getImporter(javafx, keywordService, transferRepository);
        importer.validate(fileName, null, errorCodes);
        assertTrue(errorCodes.isEmpty());
        assertEquals(bundle1, importer.getBundle());
        assertEquals(defaultCountry, importer.getCountry());
        assertEquals(hebrew, importer.getLanguage());
    }

    /**
     * Test method for
     * {@link JavaFxPropertiesImporter#validate(String, Bundle, List)}.
     */
    public final void testValidateWithUnknownBundle() throws Exception
    {
        final String fileName = "unknown.properties";
        List<ImportErrorCode> errorCodes = new ArrayList<ImportErrorCode>();
        final Importer importer = ImporterFactory.getImporter(javafx,
                keywordService, transferRepository);
        importer.validate(fileName, null, errorCodes);
        assertEquals(1, errorCodes.size());
        assertEquals(ImportErrorCode.unknownBundle, errorCodes.get(0));
    }

    /**
     * Test method for
     * {@link JavaFxPropertiesImporter#validate(String, Bundle, List)}.
     */
    public final void testValidateWithKnownBundle() throws Exception
    {
        final String fileName = "JavaFxImporterTest";
        List<ImportErrorCode> errorCodes = new ArrayList<ImportErrorCode>();
        final JavaFxPropertiesImporter importer = (JavaFxPropertiesImporter) ImporterFactory
                .getImporter(javafx, keywordService, transferRepository);
        importer.validate(fileName, null, errorCodes);
        assertTrue(errorCodes.isEmpty());
        assertEquals(bundle1, importer.getBundle());
        assertEquals(defaultCountry, importer.getCountry());
        assertEquals(defaultLanguage, importer.getLanguage());
    }

    /**
     * Test method for
     * {@link JavaFxPropertiesImporter#validate(String, Bundle, List)}.
     */
    public final void testValidateWithPresetBundle() throws Exception
    {
        final String fileName = "JavaFxImporterTest";
        List<ImportErrorCode> errorCodes = new ArrayList<ImportErrorCode>();
        final JavaFxPropertiesImporter importer = (JavaFxPropertiesImporter) ImporterFactory
                .getImporter(javafx, keywordService, transferRepository);
        Bundle bundle = new Bundle();
        bundle.setName("temp");
        
        importer.validate(fileName, bundle, errorCodes);
        assertTrue(errorCodes.isEmpty());
        assertEquals(bundle, importer.getBundle());
        assertEquals(defaultCountry, importer.getCountry());
        assertEquals(defaultLanguage, importer.getLanguage());
    }

    /**
     * Test method for
     * {@link JavaFxPropertiesImporter#validate(String, Bundle, List)}.
     */
    public final void testValidateWithUnknownCountryKnownLanguage()
            throws Exception
    {
        final String fileName = "JavaFxImporterTest_ar_AL";
        List<ImportErrorCode> errorCodes = new ArrayList<ImportErrorCode>();
        final Importer importer = ImporterFactory.getImporter(javafx,
                keywordService, transferRepository);
        importer.validate(fileName, null, errorCodes);
        assertEquals(1, errorCodes.size());
        assertEquals(ImportErrorCode.unknownCountry, errorCodes.get(0));
    }

    /**
     * Test method for
     * {@link JavaFxPropertiesImporter#validate(String, Bundle, List)}.
     */
    public final void testValidateWithIllegalCountryKnownLanguage()
            throws Exception
    {
        final String fileName = "JavaFxImporterTest_ar_AL";
        List<ImportErrorCode> errorCodes = new ArrayList<ImportErrorCode>();
        final Importer importer = ImporterFactory.getImporter(javafx,
                keywordService, transferRepository);
        importer.validate(fileName, null, errorCodes);
        assertEquals(1, errorCodes.size());
        assertEquals(ImportErrorCode.unknownCountry, errorCodes.get(0));
    }

    /**
     * Test method for
     * {@link JavaFxPropertiesImporter#validate(String, Bundle, List)}.
     */
    public final void testValidateWithKnownCountryUnknownLanguage()
            throws Exception
    {
        final String fileName = "JavaFxImporterTest_es_YE";
        List<ImportErrorCode> errorCodes = new ArrayList<ImportErrorCode>();
        final Importer importer = ImporterFactory.getImporter(javafx,
                keywordService, transferRepository);
        importer.validate(fileName, null, errorCodes);
        assertEquals(1, errorCodes.size());
        assertEquals(ImportErrorCode.unknownLanguage, errorCodes.get(0));
    }

    /**
     * Test method for
     * {@link JavaFxPropertiesImporter#validate(String, Bundle, List)}.
     */
    public final void testValidateWithKnownCountryIllegalLanguage()
            throws Exception
    {
        final String fileName = "JavaFxImporterTest_xx_YE";
        List<ImportErrorCode> errorCodes = new ArrayList<ImportErrorCode>();
        final Importer importer = ImporterFactory.getImporter(javafx,
                keywordService, transferRepository);
        importer.validate(fileName, null, errorCodes);
        assertEquals(2, errorCodes.size());
        assertTrue(errorCodes.contains(ImportErrorCode.unknownLanguage));
        assertTrue(errorCodes.contains(ImportErrorCode.illegalLanguage));
    }

    /**
     * Test method for
     * {@link JavaFxPropertiesImporter#validate(String, Bundle, List)}.
     */
    public final void testValidateWithKnownCountryKnownLanguage()
            throws Exception
    {
        final String fileName = "JavaFxImporterTest_he_IL";
        List<ImportErrorCode> errorCodes = new ArrayList<ImportErrorCode>();
        final JavaFxPropertiesImporter importer = (JavaFxPropertiesImporter) ImporterFactory
                .getImporter(javafx, keywordService, transferRepository);
        importer.validate(fileName, null, errorCodes);
        assertTrue(errorCodes.isEmpty());
        assertEquals(bundle1, importer.getBundle());
        assertEquals(israel, importer.getCountry());
        assertEquals(hebrew, importer.getLanguage());
    }

    /**
     * Test method for
     * {@link JavaFxPropertiesImporter#validate(String, Bundle, List)}.
     */
    public final void testValidateWithInvalidFilenameFormat() throws Exception
    {
        final String fileName = "JavaFxImporterTest_ar_YE_DD";
        List<ImportErrorCode> errorCodes = new ArrayList<ImportErrorCode>();
        final Importer importer = ImporterFactory.getImporter(
                javafx, keywordService, transferRepository);
        importer.validate(fileName, null, errorCodes);
        assertEquals(3, errorCodes.size());
        assertTrue(errorCodes.contains(ImportErrorCode.unknownCountry));
        assertTrue(errorCodes.contains(ImportErrorCode.unknownLanguage));
        assertTrue(errorCodes.contains(ImportErrorCode.invalidNameFormat));
    }

    /**
     * Test method for
     * {@link JavaFxPropertiesImporter#isCountryCode(String)}.
     */
    public final void testIsCountryCodeValid() throws Exception
    {
        final JavaFxPropertiesImporter importer = (JavaFxPropertiesImporter) ImporterFactory
                .getImporter(javafx, keywordService, transferRepository);
        assertTrue(importer.isCountryCode("GH"));
    }

    /**
     * Test method for
     * {@link JavaFxPropertiesImporter#isCountryCode(String)}.
     */
    public final void testIsCountryCodeInvalid() throws Exception
    {
        final JavaFxPropertiesImporter importer = (JavaFxPropertiesImporter) ImporterFactory
                .getImporter(javafx, keywordService, transferRepository);
        assertFalse(importer.isCountryCode("aa"));
    }

    /**
     * Test method for {@link JavaFxPropertiesImporter#isCountryCode(String)} when 
     * the code value is an empty string.
     */
    public final void testIsCountryCodeEmpty() throws Exception
    {
        final JavaFxPropertiesImporter importer = (JavaFxPropertiesImporter) ImporterFactory
                .getImporter(javafx, keywordService, transferRepository);
        assertFalse(importer.isCountryCode(""));
    }

    /**
     * Test method for
     * {@link JavaFxPropertiesImporter#isCountryCode(String)}.
     */
    public final void testIsCountryCodeNull() throws Exception
    {
        final JavaFxPropertiesImporter importer = (JavaFxPropertiesImporter) ImporterFactory
                .getImporter(javafx, keywordService, transferRepository);
        assertFalse(importer.isCountryCode(null));
    }

    @Override
    protected String[] getTableNames()
    {
        return new String[] {TABLE_TRANSLATION, TABLE_KEYWORD, TABLE_BUNDLE, 
                TABLE_COUNTRY, TABLE_LANGUAGE};
    }

    public void setKeywordService(KeywordService keywordService)
    {
        this.keywordService = keywordService;
    }
}
