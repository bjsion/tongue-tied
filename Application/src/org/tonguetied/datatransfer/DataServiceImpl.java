package org.tonguetied.datatransfer;

import static fmpp.setting.Settings.NAME_DATA;
import static fmpp.setting.Settings.NAME_OUTPUT_ENCODING;
import static fmpp.setting.Settings.NAME_OUTPUT_ROOT;
import static fmpp.setting.Settings.NAME_REPLACE_EXTENSIONS;
import static fmpp.setting.Settings.NAME_SOURCES;
import static fmpp.setting.Settings.NAME_SOURCE_ROOT;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.tonguetied.datatransfer.common.ExportParameters;
import org.tonguetied.datatransfer.common.FormatType;
import org.tonguetied.datatransfer.common.ImportParameters;
import org.tonguetied.datatransfer.dao.TransferRepository;
import org.tonguetied.datatransfer.exporting.ExportDataPostProcessor;
import org.tonguetied.datatransfer.exporting.ExportDataPostProcessorFactory;
import org.tonguetied.datatransfer.exporting.ExportException;
import org.tonguetied.datatransfer.importing.Importer;
import org.tonguetied.datatransfer.importing.ImporterFactory;
import org.tonguetied.keywordmanagement.KeywordService;
import org.tonguetied.keywordmanagement.Language;
import org.tonguetied.keywordmanagement.Translation;
import org.tonguetied.keywordmanagement.Language.LanguageCode;

import fmpp.ProcessingException;
import fmpp.progresslisteners.LoggerProgressListener;
import fmpp.setting.SettingException;
import fmpp.setting.Settings;

/**
 * @author bsion
 *
 */
public class DataServiceImpl implements DataService {
    private Settings settings;
    private TransferRepository transferRepository;
    private KeywordService keywordService;
    private String sourceRoot;
    private String outputRoot;
    private File outputDir;
    
    private static final File BASE_DIR = 
        new File(System.getProperty("user.dir"));
    private static final Logger logger = 
        Logger.getLogger(DataServiceImpl.class);
    private static final String DATE_FORMAT = "yyyy-MM-dd_hh_mm_ss";

    /**
     * Create a new instance of the DataServiceImpl. After this constructor
     * has been called the {@link #init()} method should be called.
     */
    public DataServiceImpl() {
    }
    
    /**
     * Initialize an instance of the DataServiceImpl. This method configures
     * the exporter for use.
     *  
     * @throws ExportException if the exporter is fails to configure
     */
    public void init() throws ExportException {
        try {
            settings = new Settings(BASE_DIR);
            settings.set(NAME_SOURCE_ROOT, sourceRoot);
//            settings.set(NAME_OUTPUT_ROOT, outputRoot);
            settings.set(NAME_OUTPUT_ENCODING, "UTF-8");
            freemarker.log.Logger.selectLoggerLibrary(
                    freemarker.log.Logger.LIBRARY_LOG4J);
        }
        catch (SettingException se) {
            throw new ExportException(se);
        }
        catch (ClassNotFoundException cnfe) {
            throw new ExportException(cnfe);
        }
    }
    
    public void exportData(final ExportParameters parameters) throws ExportException {
        if (parameters == null) {
            throw new IllegalArgumentException("cannot perform export with " +
                    "null parameters");
        }
        if (parameters.getFormatType() == null) {
            throw new IllegalArgumentException("cannot perform export without" +
                    " an export type set");
        }
        
        if (logger.isDebugEnabled()) 
            logger.debug("exporting based on filter " + parameters);
        
        try {
            File exportPath = getExportPath(true);
            settings.set(NAME_OUTPUT_ROOT, exportPath.getAbsolutePath());
            settings.set(NAME_SOURCES, 
                    getTemplateName(parameters.getFormatType()));
            String[] replaceExtensions = 
                new String[] {"ftl", parameters.getFormatType().getDefaultFileExtension()};
            settings.set(NAME_REPLACE_EXTENSIONS, replaceExtensions);
            
            List<Translation> translations = 
                transferRepository.findTranslations(parameters);
            Map<String, Object> root = postProcess(parameters, translations);
            settings.set(NAME_DATA, root);
            settings.addProgressListener(new LoggerProgressListener());
            settings.execute();
            
            if (parameters.isResultPackaged()) {
                createArchive(exportPath);
            }
        }
        catch (SettingException se) {
            throw new ExportException(se);
        }
        catch (ProcessingException pe) {
            throw new ExportException(pe);
        }
    }

    /**
     * Post process the result translations to put them into a desired format 
     * if needed.
     * 
     * @param parameters the parameters used to filter and format the data
     * @param translations the {@link Translation}s to process
     * @return a map of parameters used by the templating mechanism
     */
    private Map<String, Object> postProcess(final ExportParameters parameters,
            List<Translation> translations) {
        Map<String, Object> root = new HashMap<String, Object>();
        ExportDataPostProcessor postProcessor = 
            ExportDataPostProcessorFactory.getPostProcessor(parameters.getFormatType());
        if (postProcessor != null) {
            if (logger.isDebugEnabled()) 
                logger.debug("post processing results using: " + postProcessor.getClass());
            
            List<?> results = 
                postProcessor.transformData(translations, transferRepository);
            root.put("items", results);
//                if (parameters.getLanguages().contains(arg0))
            Language traditionalChinese = new Language();
            traditionalChinese.setCode(LanguageCode.zht);
            traditionalChinese.setName("Traditional Chinese");
            parameters.addLanguage(traditionalChinese);
            Collections.sort(parameters.getLanguages());
            root.put("languages", parameters.getLanguages());
        }
        else {
            root.put("translations", translations);
        }
        
        return root;
    }
    
    public void createArchive(File directory) throws ExportException, IllegalArgumentException {
        if (!directory.isDirectory())
            throw new IllegalArgumentException("expecting a directory");
        
        ZipOutputStream zos = null;
        try {
            File[] files = directory.listFiles();
            if (files.length > 0) {
                File archive = new File(directory, directory.getName()+".zip");
                zos = new ZipOutputStream(
                        new FileOutputStream(archive));
                for (File file : files) {
                    zos.putNextEntry(new ZipEntry(file.getName()));
                    IOUtils.write(FileUtils.readFileToByteArray(file), zos);
                    zos.closeEntry();
                }
            }
        }
        catch (IOException ioe) {
            throw new ExportException(ioe);
        }
        finally {
            IOUtils.closeQuietly(zos);
        }
    }

    /**
     * Returns the the directory where exported files from the most recently 
     * executed export are saved. This method passes a value of false to 
     * {@link #getExportPath(boolean)} so as not to reset the output path.
     * 
     * @return the output directory
     * @see #getExportPath(boolean) 
     */
    public File getExportPath() {
        return getExportPath(false);
    }

    /**
     * Returns the the directory where exported files from the most recently 
     * executed export are saved.
     * 
     * @param reset flag indicating that the output directory should be 
     * re-initialised.
     * @return the output directory 
     */
    private File getExportPath(final boolean reset) {
        if (reset) {
            final DateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
            outputDir = new File(outputRoot, formatter.format(new Date()));
        }
        
        return outputDir;
    }

    public void importData(ImportParameters parameters) {
        if (logger.isDebugEnabled()) 
            logger.debug("importing based on filter " + parameters);
        
        Importer importer = 
            ImporterFactory.getImporter(parameters.getFormatType(), keywordService);
        importer.importData(parameters);
    }

    /**
     * Determine the name of the export template to use based off the type of
     * export being performed.
     * 
     * @param formatType the type of export being performed
     * @return the name of the export template to use
     */
    private String getTemplateName(final FormatType formatType) {
        return formatType.name() + ".ftl";
    }
    
    public void setTransferRepository(TransferRepository transferRepository) {
        this.transferRepository = transferRepository;
    }

    /**
     * @param sourceRoot the directory on the file system where template files
     * are stored 
     */
    public void setSourceRoot(String sourceRoot) {
        this.sourceRoot = sourceRoot;
    }

    /**
     * @param keywordService the keywordService to set
     */
    public void setKeywordService(KeywordService keywordService) {
        this.keywordService = keywordService;
    }

    /**
     * @param outputRoot the base directory on the file system where all 
     * generated export files should be saved.
     */
    public void setOutputRoot(String outputRoot) {
        this.outputRoot = outputRoot;
    }
}
