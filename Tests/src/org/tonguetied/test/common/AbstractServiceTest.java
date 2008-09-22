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

import java.sql.Connection;

import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;
import org.springframework.test.annotation.AbstractAnnotationAwareTransactionalTests;
import org.tonguetied.keywordmanagement.BundleRepository;
import org.tonguetied.keywordmanagement.CountryRepository;
import org.tonguetied.keywordmanagement.KeywordRepository;
import org.tonguetied.keywordmanagement.LanguageRepository;
import org.tonguetied.usermanagement.UserRepository;
import org.tonguetied.utils.DBUtils;

/**
 * This is the base test class for service layer tests. This class uses the test
 * configuration files. The configuration files used are:
 * <ul>
 * <li>test-application-context.xml</li>
 * </ul>
 * This test uses the gym book sample for tests but can be overridden if needed.
 * Tests extending this class usually require a database. Each test is run
 * within a single transaction.
 * 
 * @author bsion
 * @see AbstractTransactionalDataSourceSpringContextTests
 * 
 */
public abstract class AbstractServiceTest extends
        AbstractAnnotationAwareTransactionalTests
{
    private BundleRepository bundleRepository;
    private CountryRepository countryRepository;
    private KeywordRepository keywordRepository;
    private LanguageRepository languageRepository;
    private UserRepository userRepository;
    
    protected static boolean hasDbBeenCreated = false;
    private static final String BEAN_DATASOURCE = "dataSource";
    private static final String START_DB = "startDb";

    protected static final Logger log = Logger
            .getLogger(AbstractServiceTest.class);

    static
    {
        synchronized (START_DB)
        {
            try
            {
                DBUtils.startDatabase();
            }
            catch (Exception e)
            {
                log.error(e);
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.test.AbstractTransactionalSpringContextTests#onSetUpBeforeTransaction()
     */
    @Override
    protected void onSetUpBeforeTransaction() throws Exception
    {
        // ensure the database is clean before each test
        DriverManagerDataSource ds = (DriverManagerDataSource) applicationContext
                .getBean(BEAN_DATASOURCE);
        Connection connection = DataSourceUtils.getConnection(ds);

        if (recreateSchema())
        {
            DBUtils.cleanTables(connection);
            hasDbBeenCreated = true;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.test.AbstractDependencyInjectionSpringContextTests#getConfigLocations()
     */
    @Override
    protected String[] getConfigLocations()
    {
        return new String[] { "classpath:/test-application-context.xml" };
    }

    /**
     * @return the keywordRepository
     */
    public KeywordRepository getKeywordRepository()
    {
        return keywordRepository;
    }

    /**
     * Injected by Spring.
     * 
     * @param keywordRepository the keywordRepository to set
     */
    public void setKeywordRepository(KeywordRepository keywordRepository)
    {
        this.keywordRepository = keywordRepository;
    }

    /**
     * @return the bundleRepository
     */
    public BundleRepository getBundleRepository()
    {
        return bundleRepository;
    }

    /**
     * Assign the bundleRepository.
     * 
     * @param bundleRepository the bundleRepository to set
     */
    public void setBundleRepository(BundleRepository bundleRepository)
    {
        this.bundleRepository = bundleRepository;
    }

    /**
     * @return the countryRepository
     */
    public CountryRepository getCountryRepository()
    {
        return countryRepository;
    }

    /**
     * Assign the countryRepository.
     * 
     * @param countryRepository the countryRepository to set
     */
    public void setCountryRepository(CountryRepository countryRepository)
    {
        this.countryRepository = countryRepository;
    }

    /**
     * @return the languageRepository
     */
    public LanguageRepository getLanguageRepository()
    {
        return languageRepository;
    }

    /**
     * Assign the languageRepository.
     * 
     * @param languageRepository the languageRepository to set
     */
    public void setLanguageRepository(LanguageRepository languageRepository)
    {
        this.languageRepository = languageRepository;
    }

    /**
     * @return the userRepository
     */
    public UserRepository getUserRepository()
    {
        return userRepository;
    }

    /**
     * Assign the userRepository.
     * 
     * @param userRepository the userRepository to set
     */
    public void setUserRepository(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    protected boolean recreateSchema()
    {
        return true;
    }
}
