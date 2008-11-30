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

import org.tonguetied.administration.ServerData;
import org.tonguetied.audit.AuditLogRecord;
import org.tonguetied.keywordmanagement.Bundle;
import org.tonguetied.keywordmanagement.Country;
import org.tonguetied.keywordmanagement.Keyword;
import org.tonguetied.keywordmanagement.Language;
import org.tonguetied.keywordmanagement.Translation;
import org.tonguetied.usermanagement.User;


/**
 * File containing global constants used in the testing module. 
 * 
 * @author bsion
 *
 */
public interface Constants
{
    public static final String TABLE_SERVER_DATA = 
        ServerData.class.getSimpleName().toUpperCase();
    public static final String TABLE_COUNTRY = 
        Country.class.getSimpleName().toUpperCase();
    public static final String TABLE_LANGUAGE = 
        Language.class.getSimpleName().toUpperCase();
    public static final String TABLE_BUNDLE = 
        Bundle.class.getSimpleName().toUpperCase();
    public static final String TABLE_USER = 
        User.class.getSimpleName().toUpperCase();
    public static final String TABLE_AUDIT_LOG_RECORD = 
        AuditLogRecord.class.getSimpleName().toUpperCase();
    public static final String TABLE_KEYWORD = 
        Keyword.class.getSimpleName().toUpperCase();
    public static final String TABLE_TRANSLATION = 
        Translation.class.getSimpleName().toUpperCase();
    public static final String TABLE_AUTHORITIES = "authorities".toUpperCase();
    
    public static final String[] TABLES = new String[] {
        TABLE_AUTHORITIES, TABLE_USER, TABLE_TRANSLATION, TABLE_KEYWORD,
        TABLE_AUDIT_LOG_RECORD, TABLE_LANGUAGE, TABLE_COUNTRY, TABLE_BUNDLE,
        TABLE_SERVER_DATA
    };
}
