package org.tonguetied.datatransfer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;
import org.junit.Test;
import org.springframework.test.annotation.ExpectedException;
import org.tonguetied.datatransfer.common.ExportParameters;
import org.tonguetied.datatransfer.common.FormatType;
import org.tonguetied.datatransfer.dao.TransferRepository;
import org.tonguetied.keywordmanagement.Bundle;
import org.tonguetied.keywordmanagement.Country;
import org.tonguetied.keywordmanagement.Keyword;
import org.tonguetied.keywordmanagement.Language;
import org.tonguetied.keywordmanagement.Translation;
import org.tonguetied.keywordmanagement.Country.CountryCode;
import org.tonguetied.keywordmanagement.Language.LanguageCode;
import org.tonguetied.keywordmanagement.Translation.TranslationState;
import org.tonguetied.test.common.AbstractServiceTest;

/**
 * Unit tests for export methods of the {@link DataServiceImpl} implementation
 * of the {@link DataService}.
 * 
 * @author bsion
 * 
 */
public class ExportServiceTest extends AbstractServiceTest
{
    private DataService dataService;

    private Country singapore;
    private Country australia;

    private Language english;
    private Language chinese;

    private Bundle bundle;

    private Keyword keyword1;
    private Keyword keyword2;
    private Keyword keyword3;
    private Keyword keyword4;

    private Translation translation1_1;
    private Translation translation1_2;
    private Translation translation2_1;
    private Translation translation3_1;
    private Translation translation3_2;
    private Translation translation3_3;

    private TransferRepository transferRepository;

    private File archiveTestDirectory = null;

    private static final File USER_DIR = SystemUtils.getUserDir();

    @Override
    protected void onSetUpInTransaction() throws Exception
    {
        singapore = new Country();
        singapore.setCode(CountryCode.SG);
        singapore.setName("Singapore");
        getCountryRepository().saveOrUpdate(singapore);

        australia = new Country();
        australia.setCode(CountryCode.AU);
        australia.setName("Australia");
        getCountryRepository().saveOrUpdate(australia);

        english = new Language();
        english.setCode(LanguageCode.en);
        english.setName("English");
        getLanguageRepository().saveOrUpdate(english);

        chinese = new Language();
        chinese.setCode(LanguageCode.zh);
        chinese.setName("Simplified Chinese");
        getLanguageRepository().saveOrUpdate(chinese);

        bundle = new Bundle();
        bundle.setName("testBundle");
        bundle.setResourceName("test");
        bundle.setDescription("this is a test bundle");
        getBundleRepository().saveOrUpdate(bundle);

        keyword1 = new Keyword();
        keyword1.setKeyword("akeyword");
        keyword1.setContext("keyword 1");
        translation1_1 = new Translation();
        translation1_1.setBundle(bundle);
        translation1_1.setCountry(australia);
        translation1_1.setLanguage(english);
        translation1_1.setKeyword(keyword1);
        translation1_1.setValue("a keyword");
        translation1_1.setState(TranslationState.VERIFIED);
        keyword1.addTranslation(translation1_1);
        translation1_2 = new Translation();
        translation1_2.setBundle(bundle);
        translation1_2.setCountry(singapore);
        translation1_2.setLanguage(english);
        translation1_2.setKeyword(keyword1);
        translation1_2.setValue("a keyword lah");
        translation1_2.setState(TranslationState.VERIFIED);
        keyword1.addTranslation(translation1_2);

        keyword2 = new Keyword();
        keyword2.setKeyword("anotherkeyword");
        keyword2.setContext("Keyword 2");
        translation2_1 = new Translation();
        translation2_1.setBundle(bundle);
        translation2_1.setCountry(australia);
        translation2_1.setLanguage(english);
        translation2_1.setKeyword(keyword2);
        translation2_1.setValue("another keyword");
        translation2_1.setState(TranslationState.VERIFIED);
        keyword2.addTranslation(translation2_1);

        keyword3 = new Keyword();
        keyword3.setKeyword("KeywordThree");
        translation3_1 = new Translation();
        translation3_1.setBundle(bundle);
        translation3_1.setCountry(singapore);
        translation3_1.setLanguage(chinese);
        translation3_1.setState(TranslationState.VERIFIED);
        translation3_1.setKeyword(keyword3);
        translation3_1.setValue("keyword three");
        keyword3.addTranslation(translation3_1);
        translation3_2 = new Translation();
        translation3_2.setBundle(bundle);
        translation3_2.setCountry(singapore);
        translation3_2.setLanguage(english);
        translation3_2.setKeyword(keyword3);
        translation3_2.setState(TranslationState.UNVERIFIED);
        keyword3.addTranslation(translation3_2);
        translation3_3 = new Translation();
        translation3_3.setBundle(bundle);
        translation3_3.setCountry(australia);
        translation3_3.setLanguage(english);
        translation3_3.setValue("xml < ! & \" '>");
        translation3_3.setKeyword(keyword3);
        translation3_3.setState(TranslationState.VERIFIED);
        keyword3.addTranslation(translation3_3);

        keyword4 = new Keyword();
        keyword4.setKeyword("oneOtherKeyword");
        keyword4.setContext("Keyword 4");

        getKeywordRepository().saveOrUpdate(keyword1);
        getKeywordRepository().saveOrUpdate(keyword2);
        getKeywordRepository().saveOrUpdate(keyword3);
        getKeywordRepository().saveOrUpdate(keyword4);
    }

    @Override
    protected void onTearDownAfterTransaction() throws Exception
    {
        super.onTearDownAfterTransaction();

        clearDirectory(dataService.getExportPath());
        clearDirectory(archiveTestDirectory);
    }

    private void clearDirectory(File directory)
    {
        if (directory != null)
        {
            final String name = directory.getName();
            File[] files = directory.listFiles();
            if (files != null)
            {
                for (File file : files)
                {
                    assertTrue("Failed to remove " + file, file.delete());
                }
            }

            if (directory.exists())
                assertTrue("Failed to remove " + name, directory.delete());
        }
    }

    @Test
    public final void testExportToExcel() throws Exception
    {
        ExportParameters parameters = new ExportParameters();
        parameters.addLanguage(english);
        parameters.addLanguage(chinese);
        parameters.addBundle(bundle);
        parameters.addCountry(singapore);
        parameters.setFormatType(FormatType.xls);
        parameters.setTranslationState(TranslationState.VERIFIED);
        dataService.exportData(parameters);

        final File exportDir = dataService.getExportPath();
        File[] files = exportDir.listFiles(new FileExtensionFilter(
                FormatType.xls.getDefaultFileExtension()));
        // assertEquals(1, files.length);
        String actualXML = FileUtils.readFileToString(files[0]);
        assertNotNull(actualXML);
        // assertXMLValid(actualXML);
        // CountingNodeTester countingNodeTester = new CountingNodeTester(271);
        // assertNodeTestPasses(actualXML, countingNodeTester, Node.TEXT_NODE);
        // assertXpathEvaluatesTo("17",
        // "/Workbook/Worksheet[@ss:Name=\"Translations\"]/Table/@ss:ExpandedRowCount",
        // actualXML);
        // assertXMLEqual("", actualXML);
    }

    @Test
    public final void testExportToExcelByLangauge() throws Exception
    {
        ExportParameters parameters = new ExportParameters();
        parameters.addLanguage(english);
        parameters.addLanguage(chinese);
        parameters.addBundle(bundle);
        parameters.addCountry(singapore);
        parameters.setFormatType(FormatType.xlsLanguage);
        parameters.setTranslationState(TranslationState.VERIFIED);
        dataService.exportData(parameters);

        final File exportDir = dataService.getExportPath();
        File[] files = exportDir.listFiles(new FileExtensionFilter(
                FormatType.xlsLanguage.getDefaultFileExtension()));
        // assertEquals(1, files.length);
        String actualXML = FileUtils.readFileToString(files[0]);
        assertNotNull(actualXML);
        // assertXMLValid(actualXML);
        // CountingNodeTester countingNodeTester = new CountingNodeTester(271);
        // assertNodeTestPasses(actualXML, countingNodeTester, Node.TEXT_NODE);
        // assertXpathEvaluatesTo("17",
        // "/Workbook/Worksheet[@ss:Name=\"Translations\"]/Table/@ss:ExpandedRowCount",
        // actualXML);
        // assertXMLEqual("", actualXML);
    }

    @Test
    public final void testExportToCSV() throws Exception
    {
        ExportParameters parameters = new ExportParameters();
        parameters.addLanguage(english);
        parameters.addLanguage(chinese);
        parameters.addBundle(bundle);
        parameters.addCountry(singapore);
        parameters.setFormatType(FormatType.csv);
        parameters.setTranslationState(TranslationState.VERIFIED);
        dataService.exportData(parameters);

        List<Translation> translations = transferRepository
                .findTranslations(parameters);
        StringBuilder expected = new StringBuilder();
        for (Translation t : translations)
        {
            expected.append(t.getKeyword().getKeyword()).append(",").append(
                    t.getKeyword().getContext() != null ? t.getKeyword()
                            .getContext() : "").append(",").append(
                    t.getLanguage().getName()).append(",").append(
                    t.getCountry().getName()).append(",").append(
                    t.getBundle().getName()).append(",").append(
                    t.getState().name()).append(",").append(
                    t.getValue() != null ? t.getValue() : "").append("\r\n");
        }

        final File exportDir = dataService.getExportPath();
        File[] files = exportDir.listFiles(new FileExtensionFilter(
                FormatType.csv.getDefaultFileExtension()));
        assertEquals(1, files.length);
        String actual = FileUtils.readFileToString(files[0]);

        assertEquals(expected.toString(), actual);
    }

    /**
     * Test export to a java properties file
     * 
     * @throws Exception
     */
    @Test
    public final void testExportToProperties() throws Exception
    {
        ExportParameters parameters = new ExportParameters();
        parameters.addLanguage(english);
        parameters.addLanguage(chinese);
        parameters.addBundle(bundle);
        parameters.addCountry(australia);
        parameters.addCountry(singapore);
        parameters.setFormatType(FormatType.properties);
        parameters.setTranslationState(TranslationState.VERIFIED);
        dataService.exportData(parameters);

        Properties expected_en_AU = new Properties();
        expected_en_AU.setProperty(translation1_1.getKeyword().getKeyword(),
                translation1_1.getValue());
        expected_en_AU.setProperty(translation2_1.getKeyword().getKeyword(),
                translation2_1.getValue());
        expected_en_AU.setProperty(translation3_3.getKeyword().getKeyword(),
                translation3_3.getValue());
        Properties expected_en_SG = new Properties();
        expected_en_SG.setProperty(translation1_2.getKeyword().getKeyword(),
                translation1_2.getValue());
        // expected_en_SG.setProperty(translation3_2.getKeyword().getKeyword(),
        // translation3_2.getValue());
        Properties expected_zh_SG = new Properties();
        expected_zh_SG.setProperty(translation3_1.getKeyword().getKeyword(),
                translation3_1.getValue());

        final File exportDir = dataService.getExportPath();
        File[] files = exportDir.listFiles(new FileExtensionFilter(
                ".properties"));
        assertEquals(3, files.length);
        Properties actual;
        InputStream is = null;
        Properties expected = null;
        for (File file : files)
        {
            try
            {
                if (file.getName().contains("en_AU"))
                {
                    expected = expected_en_AU;
                }
                else if (file.getName().contains("en_SG"))
                {
                    expected = expected_en_SG;
                }
                else if (file.getName().contains("zh_SG"))
                {
                    expected = expected_zh_SG;
                }
                if (expected != null)
                {
                    is = FileUtils.openInputStream(file);
                    is = new BufferedInputStream(is);
                    actual = new Properties();
                    actual.load(is);
                    assertEquals(expected, actual);
                }
            }
            finally
            {
                expected = null;
                IOUtils.closeQuietly(is);
            }
        }
    }

    /**
     * Test the export to a .Net resource file.
     * 
     * @throws Exception
     */
    public final void testExportToResouce() throws Exception
    {
        ExportParameters parameters = new ExportParameters();
        parameters.addLanguage(english);
        parameters.addLanguage(chinese);
        parameters.addBundle(bundle);
        parameters.addCountry(australia);
        parameters.addCountry(singapore);
        parameters.setFormatType(FormatType.resx);
        parameters.setTranslationState(TranslationState.VERIFIED);
        dataService.exportData(parameters);
        
        final File exportDir = dataService.getExportPath();
        File[] files = exportDir.listFiles(new FileExtensionFilter(".resx"));
        assertEquals(3, files.length);
    }

    public final void testCreateArchiveWithEmptyDir() throws Exception
    {
        archiveTestDirectory = new File(USER_DIR,
                "testCreateArchiveWithEmptyDir");
        FileUtils.forceMkdir(archiveTestDirectory);
        assertEquals(0, archiveTestDirectory.listFiles().length);
        assertTrue(archiveTestDirectory.isDirectory());
        dataService.createArchive(archiveTestDirectory);
        assertEquals(0, archiveTestDirectory.listFiles().length);
    }

    public final void testCreateArchiveWithEmptyFiles() throws Exception
    {
        archiveTestDirectory = new File(USER_DIR,
                "testCreateArchiveWithEmptyFiles");
        FileUtils.forceMkdir(archiveTestDirectory);
        FileUtils.touch(new File(archiveTestDirectory, "temp"));
        assertEquals(1, archiveTestDirectory.listFiles().length);
        assertTrue(archiveTestDirectory.isDirectory());
        dataService.createArchive(archiveTestDirectory);
        assertEquals(2, archiveTestDirectory.listFiles().length);
        // examine zip file
        File[] files = archiveTestDirectory.listFiles(new FileExtensionFilter(
                ".zip"));
        assertEquals(1, files.length);
        ZipInputStream zis = null;
        try
        {
            zis = new ZipInputStream(new FileInputStream(files[0]));
            ZipEntry zipEntry = zis.getNextEntry();
            assertEquals("temp", zipEntry.getName());
            zis.closeEntry();
        }
        finally
        {
            IOUtils.closeQuietly(zis);
        }
    }

    public final void testCreateArchiveWithFiles() throws Exception
    {
        archiveTestDirectory = new File(USER_DIR, "testCreateArchiveWithFiles");
        FileUtils.forceMkdir(archiveTestDirectory);
        FileUtils.writeStringToFile(new File(archiveTestDirectory, "temp"),
                "test.value=value");
        FileUtils.writeStringToFile(new File(archiveTestDirectory, "temp_en"),
                "test.value=valyoo");
        assertEquals(2, archiveTestDirectory.listFiles().length);
        assertTrue(archiveTestDirectory.isDirectory());
        dataService.createArchive(archiveTestDirectory);
        assertEquals(3, archiveTestDirectory.listFiles().length);
        // examine zip file
        File[] files = archiveTestDirectory.listFiles(new FileExtensionFilter(
                ".zip"));
        assertEquals(1, files.length);
        ZipInputStream zis = null;
        try
        {
            zis = new ZipInputStream(new FileInputStream(files[0]));
            ZipEntry zipEntry = zis.getNextEntry();
            assertEquals("temp", zipEntry.getName());
            zis.closeEntry();
            zipEntry = zis.getNextEntry();
            assertEquals("temp_en", zipEntry.getName());
            zis.closeEntry();
        }
        finally
        {
            IOUtils.closeQuietly(zis);
        }
    }

    @ExpectedException(IllegalArgumentException.class)
    public final void testCreateArchiveFromFile() throws Exception
    {
        archiveTestDirectory = new File(USER_DIR, "testCreateArchiveWithFiles");
        FileUtils.forceMkdir(archiveTestDirectory);
        File testFile = new File(archiveTestDirectory, "temp");
        FileUtils.writeStringToFile(testFile, "test.value=value");
        assertEquals(1, archiveTestDirectory.listFiles().length);
        assertTrue(archiveTestDirectory.isDirectory());
        dataService.createArchive(testFile);
    }

    @ExpectedException(IllegalArgumentException.class)
    public final void testExportWithNullParameters() throws Exception
    {
        dataService.exportData(null);
    }

    @ExpectedException(IllegalArgumentException.class)
    public final void testExportWithNullFormatType() throws Exception
    {
        ExportParameters parameters = new ExportParameters();
        parameters.addLanguage(english);
        parameters.addLanguage(chinese);
        parameters.addBundle(bundle);
        parameters.addCountry(singapore);
        parameters.setTranslationState(TranslationState.VERIFIED);
        parameters.setFormatType(null);
        dataService.exportData(parameters);
    }

    private static class FileExtensionFilter implements FilenameFilter
    {
        // immutable file name extension
        private String extension;

        public FileExtensionFilter(String extension)
        {
            if (StringUtils.isEmpty(extension))
                throw new IllegalArgumentException(
                        "String filter cannot be null or empty");

            this.extension = extension;
        }

        public boolean accept(File dir, String name)
        {
            return name.endsWith(extension);
        }
    }

    public void setExportService(DataService dataService)
    {
        this.dataService = dataService;
    }

    public void setTransferRepository(TransferRepository transferRepository)
    {
        this.transferRepository = transferRepository;
    }
}
