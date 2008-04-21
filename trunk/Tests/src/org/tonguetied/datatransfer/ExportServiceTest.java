package org.tonguetied.datatransfer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.springframework.test.annotation.ExpectedException;
import org.tonguetied.keywordmanagement.Bundle;
import org.tonguetied.keywordmanagement.Country;
import org.tonguetied.keywordmanagement.Keyword;
import org.tonguetied.keywordmanagement.Language;
import org.tonguetied.keywordmanagement.Translation;
import org.tonguetied.keywordmanagement.Country.CountryCode;
import org.tonguetied.keywordmanagement.Language.LanguageCode;
import org.tonguetied.test.common.AbstractServiceTest;


/**
 * @author bsion
 *
 */
public class ExportServiceTest extends AbstractServiceTest {
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
    
    @Override
    protected void onSetUpInTransaction() throws Exception {
        singapore = new Country();
        singapore.setCode(CountryCode.SG);
        singapore.setName("Singapore");
        getKeywordRepository().saveOrUpdate(singapore);
        
        australia = new Country();
        australia.setCode(CountryCode.AU);
        australia.setName("Australia");
        getKeywordRepository().saveOrUpdate(australia);
        
        english = new Language();
        english.setCode(LanguageCode.en);
        english.setName("English");
        getKeywordRepository().saveOrUpdate(english);
        
        chinese = new Language();
        chinese.setCode(LanguageCode.zh);
        chinese.setName("Simplified Chinese");
        getKeywordRepository().saveOrUpdate(chinese);
        
        bundle = new Bundle();
        bundle.setName("testBundle");
        bundle.setResourceName("test");
        bundle.setDescription("this is a test bundle");
        getKeywordRepository().saveOrUpdate(bundle);
        
        keyword1 = new Keyword();
        keyword1.setKeyword("akeyword");
        keyword1.setContext("keyword 1");
        translation1_1 = new Translation();
        translation1_1.setBundle(bundle);
        translation1_1.setCountry(australia);
        translation1_1.setLanguage(english);
        translation1_1.setKeyword(keyword1);
        translation1_1.setValue("a keyword");
        keyword1.addTranslation(translation1_1);
        translation1_2 = new Translation();
        translation1_2.setBundle(bundle);
        translation1_2.setCountry(singapore);
        translation1_2.setLanguage(english);
        translation1_2.setKeyword(keyword1);
        translation1_2.setValue("a keyword lah");
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
        keyword2.addTranslation(translation2_1);
        
        keyword3 = new Keyword();
        keyword3.setKeyword("KeywordThree");
        translation3_1 = new Translation();
        translation3_1.setBundle(bundle);
        translation3_1.setCountry(singapore);
        translation3_1.setLanguage(chinese);
        translation3_1.setKeyword(keyword3);
        translation3_1.setValue("keyword three");
        keyword3.addTranslation(translation3_1);
        translation3_2 = new Translation();
        translation3_2.setBundle(bundle);
        translation3_2.setCountry(singapore);
        translation3_2.setLanguage(english);
        translation3_2.setKeyword(keyword3);
        keyword3.addTranslation(translation3_2);
        translation3_3 = new Translation();
        translation3_3.setBundle(bundle);
        translation3_3.setCountry(australia);
        translation3_3.setLanguage(english);
        translation3_3.setValue("xml < ! & \" '>");
        translation3_3.setKeyword(keyword3);
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
    protected void onTearDownAfterTransaction() throws Exception {
        super.onTearDownAfterTransaction();
        
        final File exportDir = dataService.getExportPath();
        for (File file: exportDir.listFiles()) {
            assertTrue("Failed to remove " + file, file.delete());
        }
        
        assertTrue("Failed to remove " + exportDir, exportDir.delete());
    }

    @Test
    public final void testExportToExcel() throws Exception {
        ExportParameters parameters = new ExportParameters();
        parameters.addLanguage(english);
        parameters.addLanguage(chinese);
        parameters.addBundle(bundle);
        parameters.addCountry(singapore);
        parameters.setFormatType(FormatType.xls);
        dataService.exportData(parameters);
        
        final File exportDir = dataService.getExportPath();
        File[] files = exportDir.listFiles(
                new FileExtensionFilter(FormatType.xls.getDefaultFileExtension()));
//        assertEquals(1, files.length);
        String actualXML = FileUtils.readFileToString(files[0]);
        assertNotNull(actualXML);
//        assertXMLValid(actualXML);
//        CountingNodeTester countingNodeTester = new CountingNodeTester(271);
//        assertNodeTestPasses(actualXML, countingNodeTester, Node.TEXT_NODE);        
//        assertXpathEvaluatesTo("17",
//                "/Workbook/Worksheet[@ss:Name=\"Translations\"]/Table/@ss:ExpandedRowCount", actualXML);
//        assertXMLEqual("", actualXML);
    }
    
    @Test
    public final void testExportToExcelByLangauge() throws Exception {
        ExportParameters parameters = new ExportParameters();
        parameters.addLanguage(english);
        parameters.addLanguage(chinese);
        parameters.addBundle(bundle);
        parameters.addCountry(singapore);
        parameters.setFormatType(FormatType.xlsLanguage);
        dataService.exportData(parameters);
        
        final File exportDir = dataService.getExportPath();
        File[] files = exportDir.listFiles(
                new FileExtensionFilter(FormatType.xlsLanguage.getDefaultFileExtension()));
//        assertEquals(1, files.length);
        String actualXML = FileUtils.readFileToString(files[0]);
        assertNotNull(actualXML);
//        assertXMLValid(actualXML);
//        CountingNodeTester countingNodeTester = new CountingNodeTester(271);
//        assertNodeTestPasses(actualXML, countingNodeTester, Node.TEXT_NODE);        
//        assertXpathEvaluatesTo("17",
//                "/Workbook/Worksheet[@ss:Name=\"Translations\"]/Table/@ss:ExpandedRowCount", actualXML);
//        assertXMLEqual("", actualXML);
    }
    
    @Test
    public final void testExportToCSV() throws Exception {
        ExportParameters parameters = new ExportParameters();
        parameters.addLanguage(english);
        parameters.addLanguage(chinese);
        parameters.addBundle(bundle);
        parameters.addCountry(singapore);
        parameters.setFormatType(FormatType.csv);
        dataService.exportData(parameters);
        
        List<Translation> translations = transferRepository.findTranslations(parameters);
        StringBuilder expected = new StringBuilder();
        for (Translation t: translations) {
            expected.append(t.getKeyword().getKeyword()).append(",").
            append(t.getKeyword().getContext() != null? t.getKeyword().getContext(): "").append(",").
            append(t.getLanguage().getName()).append(",").
            append(t.getCountry().getName()).append(",").
            append(t.getBundle().getName()).append(",").
            append(t.getValue() != null? t.getValue(): "").
            append("\r\n");
        }
        
        final File exportDir = dataService.getExportPath();
        File[] files = exportDir.listFiles(
                new FileExtensionFilter(FormatType.csv.getDefaultFileExtension()));
        assertEquals(1, files.length);
        String actual = FileUtils.readFileToString(files[0]);
        
        assertEquals(expected.toString(), actual);
    }
    
    @Test
    public final void testExportToProperties() throws Exception {
        ExportParameters parameters = new ExportParameters();
        parameters.addLanguage(english);
        parameters.addLanguage(chinese);
        parameters.addBundle(bundle);
        parameters.addCountry(australia);
        parameters.addCountry(singapore);
        parameters.setFormatType(FormatType.properties);
        dataService.exportData(parameters);
        
        Properties expected_en_AU = new Properties();
        expected_en_AU.setProperty(translation1_1.getKeyword().getKeyword(), translation1_1.getValue());
        expected_en_AU.setProperty(translation2_1.getKeyword().getKeyword(), translation2_1.getValue());
        expected_en_AU.setProperty(translation3_3.getKeyword().getKeyword(), translation3_3.getValue());
        Properties expected_en_SG = new Properties();
        expected_en_SG.setProperty(translation1_2.getKeyword().getKeyword(), translation1_2.getValue());
//        expected_en_SG.setProperty(translation3_2.getKeyword().getKeyword(), translation3_2.getValue());
        Properties expected_zh_SG = new Properties();
        expected_zh_SG.setProperty(translation3_1.getKeyword().getKeyword(), translation3_1.getValue());
        
        final File exportDir = dataService.getExportPath();
        File[] files = 
            exportDir.listFiles(new FileExtensionFilter(".rawproperties"));
        assertEquals(3, files.length);
        Properties actual;
        InputStream is = null;
        Properties expected = null;
        for (File file: files) {
            try {
                if (file.getName().contains("en_AU")) {
                    expected = expected_en_AU;
                }
                else if (file.getName().contains("en_SG")) {
                    expected = expected_en_SG;
                }
                else if (file.getName().contains("zh_SG")) {
                    expected = expected_zh_SG;
                }
                if (expected != null) {
                    is = FileUtils.openInputStream(file);
                    is = new BufferedInputStream(is);
                    actual = new Properties();
                    actual.load(is);
                    assertEquals(expected, actual);
                }
            }
            finally {
                expected = null;
                IOUtils.closeQuietly(is);
            }
        }
    }
    
    @ExpectedException(IllegalArgumentException.class)
    public final void testExportWithNullParameters() throws Exception {
        dataService.exportData(null);
    }
    
    @ExpectedException(IllegalArgumentException.class)
    public final void testExportWithNullFormatType() throws Exception {
        ExportParameters parameters = new ExportParameters();
        parameters.addLanguage(english);
        parameters.addLanguage(chinese);
        parameters.addBundle(bundle);
        parameters.addCountry(singapore);
        parameters.setFormatType(null);
        dataService.exportData(parameters);
    }
    
    private class FileExtensionFilter implements FilenameFilter {
        //immutable file name extension
        private String extension;
        
        public FileExtensionFilter(String extension) {
            if (StringUtils.isEmpty(extension))
                throw new IllegalArgumentException("String filter cannot be null or empty");
            
            this.extension = extension;
        }
        
        public boolean accept(File dir, String name) {
            return name.endsWith(extension);
        }
    }
    
    public void setExportService(DataService dataService) {
        this.dataService = dataService;
    }

    public void setTransferRepository(TransferRepository transferRepository) {
        this.transferRepository = transferRepository;
    }
}
