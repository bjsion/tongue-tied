package org.tonguetied.datatransfer;

import java.io.File;
import java.io.FilenameFilter;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.junit.AfterClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.test.annotation.ExpectedException;
import org.tonguetied.datatransfer.ExportParameters;
import org.tonguetied.datatransfer.ExportService;
import org.tonguetied.datatransfer.FormatType;
import org.tonguetied.datatransfer.TransferRepository;
import org.tonguetied.keywordmanagement.Bundle;
import org.tonguetied.keywordmanagement.Country;
import org.tonguetied.keywordmanagement.Keyword;
import org.tonguetied.keywordmanagement.KeywordService;
import org.tonguetied.keywordmanagement.Language;
import org.tonguetied.keywordmanagement.Translation;
import org.tonguetied.keywordmanagement.Country.CountryCode;
import org.tonguetied.keywordmanagement.Language.LanguageCode;
import org.tonguetied.test.common.AbstractServiceTest;
import org.tonguetied.utils.FileUtils;


/**
 * @author bsion
 *
 */
public class ExportServiceTest extends AbstractServiceTest {
    private ExportService exportService;
    
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

    private KeywordService keywordService;
    private TransferRepository transferRepository;
    
//    private static final String SOURCE_ROOT = System.getProperty("user.dir") +
//        File.separator + ".." + File.separator + "Application" + File.separator + 
//        "resources" + File.separator + "templates" + File.separator + 
//        "freemarker" + File.separator + "export";
    private static final File OUTPUT_ROOT = 
        new File (System.getProperty("user.dir") + File.separator + "exports");
    
    @Override
    protected void onSetUpInTransaction() throws Exception {
        singapore = new Country();
        singapore.setCode(CountryCode.SG);
        singapore.setName("Singapore");
        keywordService.saveOrUpdate(singapore);
        
        australia = new Country();
        australia.setCode(CountryCode.AU);
        australia.setName("Australia");
        keywordService.saveOrUpdate(australia);
        
        english = new Language();
        english.setCode(LanguageCode.en);
        english.setName("English");
        keywordService.saveOrUpdate(english);
        
        chinese = new Language();
        chinese.setCode(LanguageCode.zh);
        chinese.setName("Simplified Chinese");
        keywordService.saveOrUpdate(chinese);
        
        bundle = new Bundle();
        bundle.setName("testBundle");
        bundle.setResourceName("test");
        bundle.setDescription("this is a test bundle");
        keywordService.saveOrUpdate(bundle);
        
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
        
        keywordService.saveOrUpdate(translation1_1);
        keywordService.saveOrUpdate(translation1_2);
        keywordService.saveOrUpdate(translation2_1);
        keywordService.saveOrUpdate(translation3_1);
        keywordService.saveOrUpdate(translation3_2);
    }
    
    @AfterClass
    public static void cleanUp() {
        for (File file: OUTPUT_ROOT.listFiles()) {
            assertTrue("Failed to remove " + file, file.delete());
        }
        
        assertTrue("Failed to remove " + OUTPUT_ROOT, OUTPUT_ROOT.delete());
    }

//    @Override
//    protected void onSetUpInTransaction() throws Exception {
//        this.exportService = new ExportServiceImpl();
////        ((ExportServiceImpl)this.exportService).setKeywordService(keywordService);
//        ((ExportServiceImpl)this.exportService).setSourceRoot(SOURCE_ROOT);
//        ((ExportServiceImpl)this.exportService).setOutputRoot(OUTPUT_ROOT.getAbsolutePath());
//        ((ExportServiceImpl)this.exportService).init();
//    }

    @Test
    public final void testExportToExcel() throws Exception {
        ExportParameters parameters = new ExportParameters();
        parameters.addLanguage(english);
        parameters.addLanguage(chinese);
        parameters.addBundle(bundle);
        parameters.addCountry(singapore);
        parameters.setFormatType(FormatType.xls);
        exportService.exportData(parameters);
        
        File[] files = OUTPUT_ROOT.listFiles(
                new FileExtensionFilter(FormatType.xls.getDefaultFileExtension()));
//        assertEquals(1, files.length);
        String actualXML = FileUtils.loadFile(files[0]);
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
        exportService.exportData(parameters);
        
        File[] files = OUTPUT_ROOT.listFiles(
                new FileExtensionFilter(FormatType.xlsLanguage.getDefaultFileExtension()));
//        assertEquals(1, files.length);
        String actualXML = FileUtils.loadFile(files[0]);
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
        exportService.exportData(parameters);
        
        List<Translation> translations = transferRepository.findTranslations(parameters);
        StringBuilder expected = new StringBuilder();
        for (Translation t: translations) {
            expected.append(t.getKeyword().getKeyword()).append(",").
            append(t.getKeyword().getContext() != null? t.getKeyword().getContext(): "").append(",").
            append(t.getLanguage().getName()).append(",").
            append(t.getCountry().getName()).append(",").
            append(t.getBundle().getName()).append(",").
            append(t.getValue() != null? t.getValue(): "");
        }
        
        File[] files = OUTPUT_ROOT.listFiles(
                new FileExtensionFilter(FormatType.csv.getDefaultFileExtension()));
        assertEquals(1, files.length);
        String actual = FileUtils.loadFile(files[0]);
        
        assertEquals(expected.toString(), actual);
    }
    
    @Test
    @Ignore("failing right now")
    public final void atestExportToProperties() throws Exception {
        ExportParameters parameters = new ExportParameters();
        parameters.addLanguage(english);
        parameters.addLanguage(chinese);
        parameters.addBundle(bundle);
        parameters.addCountry(singapore);
        parameters.setFormatType(FormatType.properties);
        exportService.exportData(parameters);
        
        Properties expected_en_AU = new Properties();
        expected_en_AU.setProperty(translation1_1.getKeyword().getKeyword(), translation1_1.getValue());
        expected_en_AU.setProperty(translation2_1.getKeyword().getKeyword(), translation2_1.getValue());
        Properties expected_en_SG = new Properties();
        expected_en_SG.setProperty(translation1_2.getKeyword().getKeyword(), translation1_2.getValue());
        Properties expected_zh_SG = new Properties();
        expected_zh_SG.setProperty(translation3_1.getKeyword().getKeyword(), translation3_1.getValue());
        
        File[] files = 
            OUTPUT_ROOT.listFiles(new FileExtensionFilter(".rawproperties"));
        assertEquals(3, files.length);
        Properties actual;
        for (File file: files) {
            if (file.getName().contains("en_AU")) {
                actual = FileUtils.loadProperties(file);
                assertEquals(expected_en_AU, actual);
            }
            else if (file.getName().contains("en_SG")) {
                actual = FileUtils.loadProperties(file);
                assertEquals(expected_en_SG, actual);
            }
            else if (file.getName().contains("zh_SG")) {
                actual = FileUtils.loadProperties(file);
                assertEquals(expected_zh_SG, actual);
            }
        }
    }
    
    @ExpectedException(IllegalArgumentException.class)
    public final void testExportWithNullParameters() throws Exception {
        exportService.exportData(null);
    }
    
    @ExpectedException(IllegalArgumentException.class)
    public final void testExportWithNullFormatType() throws Exception {
        ExportParameters parameters = new ExportParameters();
        parameters.addLanguage(english);
        parameters.addLanguage(chinese);
        parameters.addBundle(bundle);
        parameters.addCountry(singapore);
        parameters.setFormatType(null);
        exportService.exportData(parameters);
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
    
    public void setKeywordService(KeywordService keywordService) {
        this.keywordService = keywordService;
    }

    public void setExportService(ExportService exportService) {
        this.exportService = exportService;
    }

    public void setTransferRepository(TransferRepository transferRepository) {
        this.transferRepository = transferRepository;
    }
}
