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

import java.util.List;

import org.junit.Test;
import org.tonguetied.keywordmanagement.Keyword;
import org.tonguetied.test.common.AbstractServiceTest;

/**
 * Unit tests for methods of the {@link AuditServiceImpl} implementation of
 * the {@link QuditService}.
 * 
 * @author bsion
 * 
 */
public class AuditServiceTest extends AbstractServiceTest
{
    private Keyword keyword1;
    private AuditService auditService;
    private AuditRepository auditRepository;

    @Override
    protected void onSetUpInTransaction() throws Exception
    {
        keyword1 = new Keyword();
        keyword1.setKeyword("akeyword");
        keyword1.setContext("keyword 1");

        getKeywordRepository().saveOrUpdate(keyword1);
    }

    /**
     * Test method for {@link org.tonguetied.audit.AuditService#getAuditLog()}.
     */
    @Test
    public void testGetAuditLog()
    {
        AuditLogRecord record1 = new AuditLogRecord("test 1", keyword1, "user1");
        AuditLogRecord record2 = new AuditLogRecord("test 2", keyword1, "user1");
        AuditLogRecord record3 = new AuditLogRecord("test 3", keyword1, "user1");
        auditRepository.saveOrUpdate(record1);
        auditRepository.saveOrUpdate(record2);
        auditRepository.saveOrUpdate(record3);

        List<AuditLogRecord> auditLog = auditService.getAuditLog();
        assertEquals(3, auditLog.size());
        assertEquals(record3, auditLog.get(0));
        assertEquals(record2, auditLog.get(1));
        assertEquals(record1, auditLog.get(2));
    }

    public void setAuditService(AuditService auditService)
    {
        this.auditService = auditService;
    }

    /**
     * Assign the auditRepository.
     * 
     * @param auditRepository the auditRepository to set
     */
    public void setAuditRepository(AuditRepository auditRepository)
    {
        this.auditRepository = auditRepository;
    }
}