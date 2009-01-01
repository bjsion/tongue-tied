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

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 * Concrete implementation of the {@link SchemaDao} interface, using jdbc as
 * the persistence mechanism.
 * 
 * @author bsion
 *
 */
public class SchemaDaoImpl extends JdbcDaoSupport implements SchemaDao
{
    public void createDatabase(final String[] schemas)
    {
        if (schemas == null || schemas.length <= 0)
            throw new IllegalArgumentException("database schema cannot be null");
        
        if (logger.isDebugEnabled())
            logger.debug("attempting to create database");
        
        // execute all statements in the schema definition
        for (String schema : schemas)
        {
            for (String statement : schema.split(";"))
            {
                try
                {
                    if (logger.isDebugEnabled())
                        logger.debug("attempting to execute : " + statement);
                    if (StringUtils.isNotBlank(statement))
                        getJdbcTemplate().execute(statement);
                }
                catch (BadSqlGrammarException dae)
                {
                    if (logger.isWarnEnabled())
                        logger.warn("The following statement failed to execute: " + statement);
                }
            }
            if (logger.isInfoEnabled())
                logger.info("create schema: " + schema);
        }
    }
}
