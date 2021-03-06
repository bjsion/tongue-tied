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

import static org.tonguetied.audit.AuditLogRecord.TABLE_AUDIT_LOG_RECORD;
import static org.tonguetied.keywordmanagement.Keyword.TABLE_KEYWORD;

import java.util.List;

import org.junit.Test;
import org.tonguetied.audit.AuditLogRecord.Operation;
import org.tonguetied.keywordmanagement.Keyword;
import org.tonguetied.test.common.AbstractServiceTest;
import org.tonguetied.utils.pagination.PaginatedList;

/**
 * Unit tests for methods of the {@link AuditServiceImpl} implementation of
 * the {@link AuditService}.
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
    public final void testGetAuditLog()
    {
        AuditLogRecord record1 = new AuditLogRecord(Operation.insert, keyword1, keyword1.toString(), null, "user1");
        AuditLogRecord record2 = new AuditLogRecord(Operation.insert, keyword1, keyword1.toString(), null, "user1");
        AuditLogRecord record3 = new AuditLogRecord(Operation.insert, keyword1, keyword1.toString(), null, "user1");
        auditRepository.saveOrUpdate(record1);
        auditRepository.saveOrUpdate(record2);
        auditRepository.saveOrUpdate(record3);

        List<AuditLogRecord> auditLog = auditService.getAuditLog();
        assertEquals(3, auditLog.size());
        assertEquals(record3, auditLog.get(0));
        assertEquals(record2, auditLog.get(1));
        assertEquals(record1, auditLog.get(2));
    }

    /**
     * Test method for {@link org.tonguetied.audit.AuditService#getAuditLog(Integer, Integer)}.
     */
    @Test
    public final void testGetAuditLogWithPagination()
    {
        AuditLogRecord record1 = new AuditLogRecord(Operation.insert, keyword1, keyword1.toString(), null, "user1");
        AuditLogRecord record2 = new AuditLogRecord(Operation.insert, keyword1, keyword1.toString(), null, "user1");
        AuditLogRecord record3 = new AuditLogRecord(Operation.insert, keyword1, keyword1.toString(), null, "user1");
        auditRepository.saveOrUpdate(record1);
        auditRepository.saveOrUpdate(record2);
        auditRepository.saveOrUpdate(record3);

        PaginatedList<AuditLogRecord> auditLog = auditService.getAuditLog(0, 2);
        assertEquals(3, auditLog.getMaxListSize());
        assertEquals(2, auditLog.size());
        assertEquals(record3, auditLog.get(0));
        assertEquals(record2, auditLog.get(1));
    }

    /**
     * Test method for {@link org.tonguetied.audit.AuditService#getAuditLog(Integer, Integer)}.
     */
    @Test
    public final void testGetAuditLogWithPaginationWithInvalidFirstPositionGreaterThanSize()
    {
        AuditLogRecord record1 = new AuditLogRecord(Operation.insert, keyword1, keyword1.toString(), null, "user1");
        AuditLogRecord record2 = new AuditLogRecord(Operation.insert, keyword1, keyword1.toString(), null, "user1");
        AuditLogRecord record3 = new AuditLogRecord(Operation.insert, keyword1, keyword1.toString(), null, "user1");
        auditRepository.saveOrUpdate(record1);
        auditRepository.saveOrUpdate(record2);
        auditRepository.saveOrUpdate(record3);

        PaginatedList<AuditLogRecord> list = auditService.getAuditLog(7, 2);
        assertEquals(0, list.getMaxListSize());
        assertTrue(list.isEmpty());
    }

    /**
     * Test method for {@link org.tonguetied.audit.AuditService#getAuditLog(Integer, Integer)}.
     */
    @Test
    public final void testGetAuditLogWithPaginationWithNegativeFirstPosition()
    {
        AuditLogRecord record1 = new AuditLogRecord(Operation.insert, keyword1, keyword1.toString(), null, "user1");
        AuditLogRecord record2 = new AuditLogRecord(Operation.insert, keyword1, keyword1.toString(), null, "user1");
        AuditLogRecord record3 = new AuditLogRecord(Operation.insert, keyword1, keyword1.toString(), null, "user1");
        auditRepository.saveOrUpdate(record1);
        auditRepository.saveOrUpdate(record2);
        auditRepository.saveOrUpdate(record3);

        PaginatedList<AuditLogRecord> auditLog = auditService.getAuditLog(-1, 2);
        assertEquals(3, auditLog.getMaxListSize());
        assertEquals(2, auditLog.size());
        assertEquals(record3, auditLog.get(0));
        assertEquals(record2, auditLog.get(1));
    }

    /**
     * Test method for {@link org.tonguetied.audit.AuditService#getAuditLog(Integer, Integer)}.
     */
    @Test
    public final void testGetAuditLogWithPaginationWithNullFirstPosition()
    {
        AuditLogRecord record1 = new AuditLogRecord(Operation.insert, keyword1, keyword1.toString(), null, "user1");
        AuditLogRecord record2 = new AuditLogRecord(Operation.insert, keyword1, keyword1.toString(), null, "user1");
        AuditLogRecord record3 = new AuditLogRecord(Operation.insert, keyword1, keyword1.toString(), null, "user1");
        auditRepository.saveOrUpdate(record1);
        auditRepository.saveOrUpdate(record2);
        auditRepository.saveOrUpdate(record3);

        PaginatedList<AuditLogRecord> auditLog = auditService.getAuditLog(null, 2);
        assertEquals(3, auditLog.getMaxListSize());
        assertEquals(2, auditLog.size());
        assertEquals(record3, auditLog.get(0));
        assertEquals(record2, auditLog.get(1));
    }

    /**
     * Test method for {@link org.tonguetied.audit.AuditService#getAuditLog(Integer, Integer)}.
     */
    @Test
    public final void testGetAuditLogWithPaginationWithZeroMax()
    {
        AuditLogRecord record1 = new AuditLogRecord(Operation.insert, keyword1, keyword1.toString(), null, "user1");
        AuditLogRecord record2 = new AuditLogRecord(Operation.insert, keyword1, keyword1.toString(), null, "user1");
        AuditLogRecord record3 = new AuditLogRecord(Operation.insert, keyword1, keyword1.toString(), null, "user1");
        auditRepository.saveOrUpdate(record1);
        auditRepository.saveOrUpdate(record2);
        auditRepository.saveOrUpdate(record3);

        PaginatedList<AuditLogRecord> list = auditService.getAuditLog(0, 0);
        assertEquals(0, list.getMaxListSize());
        assertTrue(list.isEmpty());
    }

    /**
     * Test method for {@link org.tonguetied.audit.AuditService#getAuditLog(Integer, Integer)}.
     */
    @Test
    public final void testGetAuditLogWithPaginationWithNullMax()
    {
        AuditLogRecord record1 = new AuditLogRecord(Operation.insert, keyword1, keyword1.toString(), null, "user1");
        AuditLogRecord record2 = new AuditLogRecord(Operation.insert, keyword1, keyword1.toString(), null, "user1");
        AuditLogRecord record3 = new AuditLogRecord(Operation.insert, keyword1, keyword1.toString(), null, "user1");
        auditRepository.saveOrUpdate(record1);
        auditRepository.saveOrUpdate(record2);
        auditRepository.saveOrUpdate(record3);

        PaginatedList<AuditLogRecord> auditLog = auditService.getAuditLog(0, null);
        assertEquals(3, auditLog.getMaxListSize());
        assertEquals(3, auditLog.size());
        assertEquals(record3, auditLog.get(0));
        assertEquals(record2, auditLog.get(1));
        assertEquals(record1, auditLog.get(2));
    }

    /**
     * Test method for {@link org.tonguetied.audit.AuditService#getAuditLog(Integer, Integer)}.
     */
    @Test
    public final void testGetAuditLogWithPaginationWithMaxGreaterThanSize()
    {
        AuditLogRecord record1 = new AuditLogRecord(Operation.insert, keyword1, keyword1.toString(), null, "user1");
        AuditLogRecord record2 = new AuditLogRecord(Operation.insert, keyword1, keyword1.toString(), null, "user1");
        AuditLogRecord record3 = new AuditLogRecord(Operation.insert, keyword1, keyword1.toString(), null, "user1");
        auditRepository.saveOrUpdate(record1);
        auditRepository.saveOrUpdate(record2);
        auditRepository.saveOrUpdate(record3);

        PaginatedList<AuditLogRecord> auditLog = auditService.getAuditLog(0, 18);
        assertEquals(3, auditLog.getMaxListSize());
        assertEquals(3, auditLog.size());
        assertEquals(record3, auditLog.get(0));
        assertEquals(record2, auditLog.get(1));
        assertEquals(record1, auditLog.get(2));
    }

    @Override
    protected String[] getTableNames()
    {
        return new String[] {TABLE_AUDIT_LOG_RECORD, TABLE_KEYWORD};
    }

    public final void setAuditService(AuditService auditService)
    {
        this.auditService = auditService;
    }

    /**
     * Assign the auditRepository.
     * 
     * @param auditRepository the auditRepository to set
     */
    public final void setAuditRepository(AuditRepository auditRepository)
    {
        this.auditRepository = auditRepository;
    }
}
