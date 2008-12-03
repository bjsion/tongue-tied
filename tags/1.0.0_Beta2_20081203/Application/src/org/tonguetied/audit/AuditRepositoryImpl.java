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

import org.hibernate.Query;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;


/**
 * DAO facade to ORM. This facade allows access to permanent storage of Audit
 * related data via the Hibernate orm model.
 * 
 * @author bsion
 *
 */
public class AuditRepositoryImpl extends HibernateDaoSupport implements AuditRepository
{
    public List<AuditLogRecord> getAuditLog()
    {
        Query query = getSession().getNamedQuery(AuditLogRecord.QUERY_GET_AUDIT_LOG);
        return query.list();
    }

    public void saveOrUpdate(AuditLogRecord record) throws DataAccessException
    {
        getHibernateTemplate().saveOrUpdate(record);
        getHibernateTemplate().flush();
    }
    
    public void delete(AuditLogRecord record)
    {
        getSession().delete(record);
    }
}
