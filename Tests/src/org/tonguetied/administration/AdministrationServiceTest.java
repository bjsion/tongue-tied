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
package org.tonguetied.administration;

import static org.tonguetied.test.common.Constants.TABLES;
import static org.tonguetied.test.common.Constants.TABLE_SERVER_DATA;

import java.io.IOException;
import java.io.InputStream;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.jdbc.support.DatabaseMetaDataCallback;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jdbc.support.MetaDataAccessException;
import org.springframework.test.annotation.ExpectedException;
import org.tonguetied.keywordmanagement.Bundle;
import org.tonguetied.keywordmanagement.Country;
import org.tonguetied.keywordmanagement.Language;
import org.tonguetied.keywordmanagement.Country.CountryCode;
import org.tonguetied.keywordmanagement.Language.LanguageCode;
import org.tonguetied.test.common.AbstractServiceTest;
import org.tonguetied.usermanagement.User;
import org.tonguetied.utils.database.EmbeddedDatabaseServer;

/**
 * Unit tests for methods of the {@link AdministrationServiceImpl} 
 * implementation of the {@link AdministrationService}.
 * 
 * @author bsion
 *
 */
public class AdministrationServiceTest extends AbstractServiceTest
{
    private AdministrationService administrationService;
    @Override
    protected void onSetUpBeforeTransaction() throws Exception
    {
        dropSchema();
    }
    
    /**
     * Test method for {@link org.tonguetied.administration.AdministrationServiceImpl#getLatestData()}.
     */
    @Test
    public final void testGetLatestDataWithNoSchema() throws Exception
    {
//        dropSchema();
        ServerData actual = administrationService.getLatestData();
        assertNull(actual);
    }

    /**
     * Test method for {@link org.tonguetied.administration.AdministrationServiceImpl#getLatestData()}.
     */
    @Test
    public final void testGetLatestData()
    {
        executeSqlScript("/hsql-schema.sql", true);
        ServerData version1 = new ServerData("1.0.0", "1288", new Date(11111111));
        ServerData version2 = new ServerData("1.1.0", "1551", new Date(999999999));
        
        administrationService.saveOrUpdate(version1);
        administrationService.saveOrUpdate(version2);
        
        ServerData actual = administrationService.getLatestData();
        assertEquals(version2, actual);
    }

    /**
     * Test method for {@link org.tonguetied.administration.AdministrationServiceImpl#getLatestData()}.
     */
    @Test
    public final void testGetLatestDataWithNoData()
    {
        executeSqlScript("/hsql-schema.sql", true);
        ServerData actual = administrationService.getLatestData();
        assertNull(actual);
    }
    
    @Test
    public final void testCreateDatabase() throws Exception
    {
//        dropSchema();
        final String[] schemas = new String[] { loadSchema("/hsql-schema.sql"), 
                loadSchema("/initial-data.sql")};
        administrationService.createDatabase(schemas);
        Set<String> tables = (Set<String>) JdbcUtils.extractDatabaseMetaData(
                jdbcTemplate.getDataSource(), new GetTableNames());
        assertEquals(TABLES.length, tables.size());
        for (int i=0; i < TABLES.length; i++)
            assertTrue(tables.contains(TABLES[i]));
        User admin = getUserRepository().getUser("admin");
        assertNotNull(admin);
        Language language = getLanguageRepository().getLanguage(LanguageCode.DEFAULT);
        assertNotNull(language);
        Country country = getCountryRepository().getCountry(CountryCode.DEFAULT);
        assertNotNull(country);
        Bundle bundle = getBundleRepository().getDefaultBundle();
        assertNotNull(bundle);
    }

//    @Test
//    public final void testCreateDatabaseOverExistingDatabase() throws Exception
//    {
//        Set<String> tables = (Set<String>) JdbcUtils.extractDatabaseMetaData(super.jdbcTemplate.getDataSource(), new GetTableNames());
//        assertEquals(TABLES.length, tables.size());
//        for (int i=0; i < TABLES.length; i++)
//            assertTrue(tables.contains(TABLES[i]));
//        final String[] schemas = new String[] { loadSchema("/hsql-schema.sql"), 
//                loadSchema("/initial-data.sql")};
//        administrationService.createDatabase(schemas);
//    }
//    @Test
//    public final void testCreateDatabaseWithInvalidSchema() throws Exception
//    {
//        executeSqlScript("/drop-schema.sql", true);
//        Set<String> tables = (Set<String>) JdbcUtils.extractDatabaseMetaData(
//                jdbcTemplate.getDataSource(), new GetTableNames());
//        assertTrue(tables.isEmpty());
//        final String[] schemas = new String[] {loadSchema("/invalid-schema.sql")};
//        administrationService.createDatabase(schemas);
//    }

    @Test
    @ExpectedException(DataAccessResourceFailureException.class)
    public final void testCreateDatabaseWhenDatabaseNotExist() throws Exception
    {
        EmbeddedDatabaseServer.stopDatabase();
        final String[] schemas = new String[] { loadSchema("/hsql-schema.sql"), 
                loadSchema("/initial-data.sql")};
        administrationService.createDatabase(schemas);
    }
    
    @Test(expected=IllegalArgumentException.class)
    @ExpectedException(IllegalArgumentException.class)
    public final void testCreateDatabaseWithNullSchema()
    {
        administrationService.createDatabase(null);
    }

    @Test(expected=IllegalArgumentException.class)
    @ExpectedException(IllegalArgumentException.class)
    public final void testCreateDatabaseWithEmptySchema()
    {
        administrationService.createDatabase(new String[]{});
    }

    /**
     * @throws IOException
     */
    private String loadSchema(final String schemaFile) throws IOException
    {
        InputStream is = null;
        String schema;
        try
        {
            is = AdministrationServiceTest.class.getResourceAsStream(schemaFile);
            schema = IOUtils.toString(is);
        }
        finally 
        {
            IOUtils.closeQuietly(is);
        }
        return schema;
    }
    
    private void dropSchema() throws DataAccessException,
            MetaDataAccessException
    {
        executeSqlScript("/drop-schema.sql", true);
        Set<String> tables = (Set<String>) JdbcUtils.extractDatabaseMetaData(
                jdbcTemplate.getDataSource(), new GetTableNames());
        assertTrue(tables.isEmpty());
    }

    @Override
    protected String[] getTableNames()
    {
        return new String[] {TABLE_SERVER_DATA};
    }

    /**
     * Assign the administrationService.
     *
     * @param administrationService the administrationService to set
     */
    public void setAdministrationService(AdministrationService administrationService)
    {
        this.administrationService = administrationService;
    }
    
    private class GetTableNames implements DatabaseMetaDataCallback
    {

        public Object processMetaData(DatabaseMetaData metaData)
                throws SQLException
        {
            ResultSet rs = metaData.getTables(null, null, null, new String[] {"TABLE"});
            
            Set<String> tables = new HashSet<String>();
            while (rs.next())
            {
                tables.add(rs.getString("TABLE_NAME"));
            }
            
            return tables;
        }
    }
}
