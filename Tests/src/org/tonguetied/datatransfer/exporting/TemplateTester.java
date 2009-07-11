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

import static fmpp.setting.Settings.NAME_DATA;
import static fmpp.setting.Settings.NAME_OUTPUT_ENCODING;
import static fmpp.setting.Settings.NAME_OUTPUT_ROOT;
import static fmpp.setting.Settings.NAME_REPLACE_EXTENSIONS;
import static fmpp.setting.Settings.NAME_SOURCES;
import static fmpp.setting.Settings.NAME_SOURCE_ROOT;
import static freemarker.log.Logger.LIBRARY_LOG4J;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.fail;
import static org.tonguetied.datatransfer.exporting.Constants.TEST_CONFIG_DIR;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.tonguetied.keywordmanagement.Translation;

import fmpp.ProcessingException;
import fmpp.progresslisteners.LoggerProgressListener;
import fmpp.setting.Settings;

/**
 * Base class for testing templates.
 * 
 * @author bsion
 *
 */
public abstract class TemplateTester 
{
    private List<Translation> translations;
    private final String templateName;
    private final String outputExtension;
    private String[] outputExtensions;
    
    private static File templateDir;
    private static File outputDir;
    
    protected static final Logger logger = Logger.getLogger(TemplateTester.class);
    
    /**
     * Create a new instance of TemplateTester.
     * 
     * @param templateName the name of the template to test
     * @param outputExtension the file extension string
     */
    public TemplateTester(final String templateName, final String outputExtension)
    {
        this.templateName = templateName;
        this.outputExtension = outputExtension;
        this.outputExtensions = new String[] {outputExtension};
    }

    /**
     * Load template variables.
     * 
     * @throws Exception if the variables fail to load
     */
    @BeforeClass
    public static void initialize() throws Exception
    {
        Properties properties = new Properties();
        ByteArrayInputStream bais = null;
        try
        {
            byte[] bytes = FileUtils.readFileToByteArray(
                    new File(TEST_CONFIG_DIR, "templateTest.properties"));
            bais = new ByteArrayInputStream(bytes);
            properties.load(bais);
            templateDir = new File(properties.getProperty("source.root"));
            outputDir = new File(properties.getProperty("output.root"));
            if (!outputDir.mkdir())
                logger.error("failed to create directory " + outputDir);
        }
        finally
        {
            if (bais != null)
                bais.close();
        }
    }
    
    @Before
    public void setUp() throws Exception
    {
        if (!getOutputDir().isDirectory())
            FileUtils.forceMkdir(getOutputDir());
    }
    
    @After
    public void destroy() throws Exception
    {
        FileUtils.deleteDirectory(outputDir);
    }
    
    /**
     * Execute any assertions that need to be made after processing a template
     * with translations.
     * 
     * @throws Exception
     */
    public abstract void runAssertions() throws Exception;
    
    @Test
    public final void testTemplate() throws Exception 
    {
        assertNotNull(translations);
        try
        {
        processTemplate();
        }
        catch (Exception e)
        {
            fail(e.getMessage());
        }
        
        runAssertions();
    }

    @Test(expected=ProcessingException.class)
    public final void testTemplateWithNullTranslations() throws Exception
    {
        translations = null;
        processTemplate();
    }
    
    @Test
    public void testTemplateWithEmptyTranslations() throws Exception
    {
        translations = new ArrayList<Translation>();
        processTemplate();
        
        Collection<File> files = FileUtils.listFiles(getOutputDir(), outputExtensions, false);
        assertEquals(0, files.size());
    }
    
    protected final void processTemplate() throws Exception
    {
        long start = System.currentTimeMillis();
        
        Settings settings = new Settings(SystemUtils.getUserDir());
        settings.set(NAME_SOURCE_ROOT, templateDir.getAbsolutePath());
        settings.set(NAME_OUTPUT_ENCODING, "UTF-8");
        freemarker.log.Logger.selectLoggerLibrary(LIBRARY_LOG4J);
        settings.set(NAME_REPLACE_EXTENSIONS, new String[] {"ftl", outputExtension});

        settings.set(NAME_OUTPUT_ROOT, outputDir.getAbsolutePath());
        settings.set(NAME_SOURCES, templateName);
        
        Map<String, Object> root = new HashMap<String, Object>();
        root.put("translations", translations);
        // TODO: follow best practice and put in the configuration as a 
        // shared variable, see: http://freemarker.sourceforge.net/docs/pgui_datamodel_directive.html
//        TemplateEnvironment.getCurrentInstance().getFreemarkerEnvironment().getConfiguration().setSharedVariable("native2ascii", new Native2AsciiDirective());
        root.put("native2ascii", new Native2AsciiDirective());
        settings.set(NAME_DATA, root);
        settings.addProgressListener(new LoggerProgressListener());
        settings.execute();
        
        if (logger.isInfoEnabled()) {
            float totalMillis = System.currentTimeMillis() - start;
            logger.info("import complete in " + (totalMillis/1000) + " seconds");
        }
    }
    
    /**
     * @param translations the translations to set
     */
    public final void setTranslations(final List<Translation> translations)
    {
        this.translations = translations;
    }

    /**
     * @return the templateDir
     */
    public final static File getTemplateDir()
    {
        return templateDir;
    }

    /**
     * @param templateDir the templateDir to set
     */
    public final static void setTemplateDir(final File templateDir)
    {
        TemplateTester.templateDir = templateDir;
    }

    /**
     * @return the outputDir
     */
    public final static File getOutputDir()
    {
        return outputDir;
    }

    /**
     * @param outputDir the outputDir to set
     */
    public final static void setOutputDir(final File outputDir) {
        TemplateTester.outputDir = outputDir;
    }

    /**
     * @return the outputExtensions
     */
    public final String[] getOutputExtensions()
    {
        return outputExtensions.clone();
    }
}
