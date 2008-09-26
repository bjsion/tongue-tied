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
package org.tonguetied.web.servlet;

import java.util.Collections;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

/**
 * Listener that initializes application wide settings when the HTTP server is 
 * first started and tidies up any resources when the servletContext is 
 * destroyed.
 * 
 * @author bsion
 *
 */
public class ServletContextInitializer implements ServletContextListener {

    private static final String KEY_SUPPORTED_LANGUAGES = "supportedLanguages";
    private static final String LANGUAGE_PROPERTIES = "language";
    private static final Logger logger = 
        Logger.getLogger(ServletContextInitializer.class);
    
    /* (non-Javadoc)
     * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent event) {
    }

    /**
     * Perform server servlet initialization. When the servlet is first 
     * initialized set the server system properties. These server properties 
     * are used in the domain layer, but are not known until the web 
     * application is deployed and started. Also loads application wide 
     * variables including:
     * <ul>
     *   <li>the list of supported languages</li>
     * </ul>
     *   
     * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent event) {
        // read language.properties
        if (logger.isInfoEnabled()) 
            logger.info("loading resources from file " + LANGUAGE_PROPERTIES);
        ResourceBundle bundle = 
            ResourceBundle.getBundle(LANGUAGE_PROPERTIES);
        Set<String> languageKeys = 
            new TreeSet<String>(Collections.list(bundle.getKeys()));
        if (languageKeys.isEmpty()) {
            logger.warn("Resource file "+
                    LANGUAGE_PROPERTIES+".properties contains no entries");
            languageKeys.add(Locale.ENGLISH.getLanguage());
        }
        
        event.getServletContext().setAttribute(
                KEY_SUPPORTED_LANGUAGES, languageKeys);
    }
}
