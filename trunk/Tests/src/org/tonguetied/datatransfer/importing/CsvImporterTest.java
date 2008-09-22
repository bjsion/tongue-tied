package org.tonguetied.datatransfer.importing;

import static org.tonguetied.datatransfer.importing.Constants.TEST_DATA_DIR;

import java.io.File;
import java.util.List;
import java.util.SortedSet;

import org.apache.commons.io.FileUtils;
import org.tonguetied.datatransfer.common.FormatType;
import org.tonguetied.datatransfer.common.ImportParameters;
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
 * Class contain unit tests for the {@link CsvImporter} class. Used rules for 
 * csv format from 
 * <a href="http://www.creativyst.com/Doc/Articles/CSV/CSV01.htm>CSV File 
 * format</a>
 * 
 * @author bsion
 * 
 */
public final class CsvImporterTest extends AbstractServiceTest
{

    private KeywordService keywordService;
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
        bundle1.setResourceName("CsvImporterTest");
        bundle1.setGlobal(false);

        bundle2 = new Bundle();
        bundle2.setName("bundle2");
        bundle2.setResourceName("bundle2");
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
     * {@link CsvImporter#importData(ImportParameters)}.
     */
    public final void testImportData() throws Exception
    {
        final String fileName = "CsvImporterTest";
        File file = new File(TEST_DATA_DIR, fileName + "."
                + FormatType.csv.getDefaultFileExtension());
        byte[] input = FileUtils.readFileToByteArray(file);
        final Importer importer = ImporterFactory.getImporter(FormatType.csv,
                keywordService);
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
        assertEquals(TranslationState.UNVERIFIED, actualTranslation.getState());
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
        assertEquals(TranslationState.UNVERIFIED, actualTranslation.getState());
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
        assertEquals(TranslationState.UNVERIFIED, actualTranslation.getState());
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
        assertEquals(TranslationState.UNVERIFIED, actualTranslation.getState());
        assertEquals("a line break\nin the value", actualTranslation.getValue());

        actual = keywordService.getKeyword("import.test.five");
        assertNotNull(actual);
        translations = actual.getTranslations();
        assertEquals(2, translations.size());
        actualTranslations = actual.getTranslations().toArray();
        actualTranslation = (Translation) actualTranslations[0];
        assertEquals(defaultCountry, actualTranslation.getCountry());
        assertEquals(defaultLanguage, actualTranslation.getLanguage());
        assertEquals(bundle1, actualTranslation.getBundle());
        assertEquals(TranslationState.UNVERIFIED, actualTranslation.getState());
        assertEquals("some text, with ! reserved chars # \"all, good\"", actualTranslation.getValue());
    }

    /**
     * Test method for
     * {@link CsvImporter#importData(ImportParameters)}.
     */
    public final void testImportWithIllegalAttributes() throws Exception
    {
        final String fileName = "CsvImporterTestWithIllegalAttributes";
        File file = new File(TEST_DATA_DIR, fileName + "."
                + FormatType.csv.getDefaultFileExtension());
        byte[] input = FileUtils.readFileToByteArray(file);
        final Importer importer = ImporterFactory.getImporter(FormatType.csv,
                keywordService);
        TranslationState expectedState = TranslationState.VERIFIED;
        ImportParameters parameters = new ImportParameters();
        parameters.setData(input);
        parameters.setTranslationState(expectedState);
        parameters.setFileName(fileName);
        try 
        {
            importer.importData(parameters);
            fail();
        }
        catch (ImportException ie)
        {
            List<ImportErrorCode> errorCodes = ie.getErrorCodes();
            assertEquals(3, errorCodes.size());
            assertTrue(errorCodes.contains(ImportErrorCode.illegalCountry));
            assertTrue(errorCodes.contains(ImportErrorCode.illegalLanguage));
            assertTrue(errorCodes.contains(ImportErrorCode.illegalTranslationState));
        }
        Keyword actual = keywordService.getKeyword("import.test.one");
        assertNotNull(actual);
        SortedSet<Translation> translations = actual.getTranslations();
        assertEquals(1, translations.size());
        Object[] actualTranslations = actual.getTranslations().toArray();
        Translation actualTranslation = (Translation) actualTranslations[0];
        assertEquals(defaultCountry, actualTranslation.getCountry());
        assertEquals(defaultLanguage, actualTranslation.getLanguage());
        assertEquals(bundle1, actualTranslation.getBundle());
        assertEquals(TranslationState.UNVERIFIED, actualTranslation.getState());
        assertEquals("Value 1", actualTranslation.getValue());

        actual = keywordService.getKeyword("import.test.two");
        assertNull(actual);
        actual = keywordService.getKeyword("import.test.six");
        assertNull(actual);
    }

    /**
     * Test method for
     * {@link CsvImporter#importData(ImportParameters)}.
     */
    public final void testImportWithUnknownAttributes() throws Exception
    {
        final String fileName = "CsvImporterTestWithUnknownAttributes";
        File file = new File(TEST_DATA_DIR, fileName + "."
                + FormatType.csv.getDefaultFileExtension());
        byte[] input = FileUtils.readFileToByteArray(file);
        final Importer importer = ImporterFactory.getImporter(FormatType.csv,
                keywordService);
        TranslationState expectedState = TranslationState.VERIFIED;
        ImportParameters parameters = new ImportParameters();
        parameters.setData(input);
        parameters.setTranslationState(expectedState);
        parameters.setFileName(fileName);
        try 
        {
            importer.importData(parameters);
            fail();
        }
        catch (ImportException ie)
        {
            List<ImportErrorCode> errorCodes = ie.getErrorCodes();
            assertEquals(3, errorCodes.size());
            assertTrue(errorCodes.contains(ImportErrorCode.unknownBundle));
            assertTrue(errorCodes.contains(ImportErrorCode.unknownCountry));
            assertTrue(errorCodes.contains(ImportErrorCode.unknownLanguage));
        }
        Keyword actual = keywordService.getKeyword("import.test.one");
        assertNotNull(actual);
        SortedSet<Translation> translations = actual.getTranslations();
        assertEquals(1, translations.size());
        Object[] actualTranslations = actual.getTranslations().toArray();
        Translation actualTranslation = (Translation) actualTranslations[0];
        assertEquals(defaultCountry, actualTranslation.getCountry());
        assertEquals(defaultLanguage, actualTranslation.getLanguage());
        assertEquals(bundle1, actualTranslation.getBundle());
        assertEquals(TranslationState.UNVERIFIED, actualTranslation.getState());
        assertEquals("Value 1", actualTranslation.getValue());

        actual = keywordService.getKeyword("import.test.two");
        assertNull(actual);
        actual = keywordService.getKeyword("import.test.six");
        assertNull(actual);
    }
    
    public void setKeywordService(KeywordService keywordService)
    {
        this.keywordService = keywordService;
    }
}
