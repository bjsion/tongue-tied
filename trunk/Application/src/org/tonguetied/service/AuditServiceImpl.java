package org.tonguetied.service;

import java.util.List;

import org.tonguetied.dao.DaoRepository;
import org.tonguetied.domain.AuditLogRecord;


/**
 * @author bsion
 *
 */
public class AuditServiceImpl implements AuditService {
    private DaoRepository daoRepository;

    public List<AuditLogRecord> getAuditLog() {
        return daoRepository.getAuditLog();
    }

    /**
     * @param daoRepository the daoRepository to set
     */
    public void setDaoRepository(DaoRepository daoRepository) {
        this.daoRepository = daoRepository;
    }
}
