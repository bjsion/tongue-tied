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
package org.tonguetied.test.common;

import static org.tonguetied.utils.Constants.HIBERNATE_DIALECT;
import static org.tonguetied.utils.Constants.JDBC_DRIVER;
import static org.tonguetied.utils.Constants.JDBC_PASSWORD;
import static org.tonguetied.utils.Constants.JDBC_URL;
import static org.tonguetied.utils.Constants.JDBC_USER_NAME;
import static org.tonguetied.utils.DBUtils.props;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.junit.Before;
import org.junit.BeforeClass;
import org.tonguetied.audit.AuditLogRecord;
import org.tonguetied.keywordmanagement.Bundle;
import org.tonguetied.keywordmanagement.Country;
import org.tonguetied.keywordmanagement.Keyword;
import org.tonguetied.keywordmanagement.Language;
import org.tonguetied.keywordmanagement.Translation;
import org.tonguetied.usermanagement.User;
import org.tonguetied.usermanagement.UserRight;
import org.tonguetied.utils.DBUtils;



public class PersistenceTestBase {
    
    private static SessionFactory factory;
    
    protected static final Logger logger = 
        Logger.getLogger(PersistenceTestBase.class);
    
    private static final Class<?>[] ANNOTATED_CLASSES = 
        new Class[]{AuditLogRecord.class,
                    Bundle.class, 
                    Keyword.class, 
                    Language.class, 
                    Translation.class, 
                    Country.class,
                    User.class,
                    UserRight.class};
    
    @BeforeClass
    public static void initialize() throws Exception {
        DBUtils.startDatabase();
        
        AnnotationConfiguration config = new AnnotationConfiguration();
        config.setProperty(Environment.DRIVER, props.getProperty(JDBC_DRIVER));
        config.setProperty(Environment.URL, props.getProperty(JDBC_URL));
        config.setProperty(Environment.USER, props.getProperty(JDBC_USER_NAME));
        config.setProperty(Environment.PASS, props.getProperty(JDBC_PASSWORD));
        config.setProperty(Environment.POOL_SIZE, "1");
        config.setProperty(Environment.AUTOCOMMIT, "true");
        config.setProperty(Environment.CACHE_PROVIDER, "org.hibernate.cache.HashtableCacheProvider");
        config.setProperty(Environment.SHOW_SQL, "false");
        config.setProperty(Environment.USE_SECOND_LEVEL_CACHE, "false");
        config.setProperty(Environment.DIALECT, props.getProperty(HIBERNATE_DIALECT));
        
        for (int i=0; i<ANNOTATED_CLASSES.length; i++) {
            config.addAnnotatedClass(ANNOTATED_CLASSES[i]);
        }
        
        setSessionFactory(config.buildSessionFactory());
    }
    
    @Before
    public void setUp() throws Exception {
        if (recreateSchema()) {
            DBUtils.cleanTables(getSession().connection());
        }
    }
    
    protected static synchronized Session getSession() {
        if (factory == null) {
            factory = new Configuration().configure().buildSessionFactory();
        }
        return factory.openSession();
    }

    protected static void setSessionFactory(SessionFactory factory) {
        PersistenceTestBase.factory = factory;
    }

    protected static boolean recreateSchema() {
        return true;
    }
}
