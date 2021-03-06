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
package org.tonguetied.audit;

import junitx.extensions.EqualsHashCodeTestCase;

import org.tonguetied.audit.AuditLogRecord.Operation;
import org.tonguetied.keywordmanagement.Keyword;

/**
 * Test of equals and hashCode method for the class {@link AuditLogRecord}.
 * 
 * @author bsion
 * 
 */
public class AuditLogRecordEqualsHashCodeTest extends EqualsHashCodeTestCase
{

    public AuditLogRecordEqualsHashCodeTest(String name)
    {
        super(name);
    }

    @Override
    protected Object createInstance() throws Exception
    {
        Keyword keyword = new Keyword();
        keyword.setKeyword("test");

        AuditLogRecord record = new AuditLogRecord(Operation.insert, keyword,
                keyword.toString(), null, "user");

        return record;
    }

    @Override
    protected Object createNotEqualInstance() throws Exception
    {
        Keyword keyword = new Keyword();
        keyword.setKeyword("test");

        AuditLogRecord record = new AuditLogRecord(Operation.delete, keyword,
                keyword.toString(), null, "user");

        return record;
    }
}
