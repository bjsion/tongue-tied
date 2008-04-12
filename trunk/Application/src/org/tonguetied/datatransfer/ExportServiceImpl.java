package org.tonguetied.datatransfer;

import static fmpp.setting.Settings.NAME_DATA;
import static fmpp.setting.Settings.NAME_OUTPUT_ENCODING;
import static fmpp.setting.Settings.NAME_OUTPUT_ROOT;
import static fmpp.setting.Settings.NAME_REPLACE_EXTENSIONS;
import static fmpp.setting.Settings.NAME_SOURCES;
import static fmpp.setting.Settings.NAME_SOURCE_ROOT;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
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
public class ExportServiceImpl implements ExportService {
    private Settings settings;
    private TransferRepository transferRepository;
    private KeywordService keywordService;
    private String sourceRoot;
    private String outputRoot;
    
    private static final File BASE_DIR = 
        new File(System.getProperty("user.dir"));
    private static final Logger logger = 
        Logger.getLogger(ExportServiceImpl.class);
    private static final DateFormat DATE_FORMAT = 
        new SimpleDateFormat("yyyy-MM-dd_hh_ss");
    private String outputDir;

    /**
     * Create a new instance of the ExportServiceImpl. After this constructor
     * has been called the {@link #init()} method should be called.
     */
    public ExportServiceImpl() {
    }
    
    /**
     * Initialize an instance of the ExportServiceImpl. This method configures
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
    
    /* (non-Javadoc)
     * @see org.tonguetied.service.ExportService#export(org.tonguetied.domain.ExportParameters)
     */
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
            settings.set(NAME_OUTPUT_ROOT, getExportPath(true));
            settings.set(NAME_SOURCES, 
                    getTemplateName(parameters.getFormatType()));
            String[] replaceExtensions = 
                new String[] {"ftl", parameters.getFormatType().getDefaultFileExtension()};
            settings.set(NAME_REPLACE_EXTENSIONS, replaceExtensions);
            
            List<Translation> translations = 
                transferRepository.findTranslations(parameters);
            Map<String, Object> root = new HashMap<String, Object>();
            ExportDataPostProcessor postProcessor = parameters.getFormatType().getPostProcessor();
            if (postProcessor != null) {
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
            settings.set(NAME_DATA, root);
            settings.addProgressListener(new LoggerProgressListener());
            settings.execute();
        }
        catch (SettingException se) {
            throw new ExportException(se);
        }
        catch (ProcessingException pe) {
            throw new ExportException(pe);
        }
    }

    /**
     * Returns the absolute pathname of the the directory where exported files 
     * from the most recently executed export are saved. This method pass in a
     * value of false to {@link #getExportPath(boolean)} so as not to reset the
     * output path.
     * 
     * @return the absolute path of the output directory
     * @see #getExportPath(boolean) 
     */
    public String getExportPath() {
        return getExportPath(false);
    }

    /**
     * Returns the absolute pathname of the the directory where exported files 
     * from the most recently executed export are saved.
     * 
     * @param reset flag indicating that the output directory should be 
     * re-initialised.
     * @return the absolute path of the output directory 
     */
    private String getExportPath(final boolean reset) {
        if (reset) {
            outputDir = outputRoot + File.separator + DATE_FORMAT.format(new Date());
        }
        
        return outputDir;
    }

    /* (non-Javadoc)
     * @see org.tonguetied.service.ExportService#importData()
     */
    public void importData(byte[] data, FormatType formatType) {
        Importer importer = Importer.createInstance(keywordService, formatType);
        importer.importData(data);
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
