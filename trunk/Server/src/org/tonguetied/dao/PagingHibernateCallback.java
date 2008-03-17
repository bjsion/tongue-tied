/**
 * 
 */
package org.tonguetied.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 * This class adds pagination to a {@link Query} or {@link Criteria}.
 * 
 * @author bsion
 * @see http://blog.hibernate.org/cgi-bin/blosxom.cgi/2004/08/14#fn.html
 */
public class PagingHibernateCallback implements HibernateCallback {
    
    private List results;
    private Integer page;
    private Integer pageSize;
    private Query query;
    private Criteria criteria;
    
    /**
     * Create a new instance of PagingHibernateCallback.
     * 
     */
    private PagingHibernateCallback(Integer page, Integer pageSize) {
        this.page = page;
        this.pageSize = pageSize;
    }
    
    /**
     * Create a new instance of PagingHibernateCallback.
     * 
     * @param query the query to execute, which determines the value in the 
     * result list
     * @param page the page of the list to display
     * @param pageSize the size of the results per page
     */
    public PagingHibernateCallback(Query query, Integer page, Integer pageSize) {
        this(page, pageSize);
        this.query = query;
        this.criteria = null;
    }

    /**
     * Create a new instance of PagingHibernateCallback.
     * 
     * @param criteria the criteria to execute, which determines the value in 
     * the result list
     * @param page the page of the list to display
     * @param pageSize the size of the results per page
     */
    public PagingHibernateCallback(Criteria criteria, Integer page, Integer pageSize) {
        this(page, pageSize);
        this.criteria = criteria;
        this.query = null;
    }

    /* (non-Javadoc)
     * @see org.springframework.orm.hibernate3.HibernateCallback#doInHibernate(org.hibernate.Session)
     */
    public Object doInHibernate(Session session) throws HibernateException,
            SQLException {
        
        if (query != null) {
            this.results = processQuery();
        }
        else if (criteria != null) {
            this.results = processCriteria();
        }
//        if (this.page == -1) this.page = 0; //ZERO Rows.
//        ServiceRepository.currentPageNumber.set(page);
        
        return this.results;
    }

    private List processQuery() {
        if (pageSize != null) {
            query.setMaxResults(pageSize + 1);
            if (page != null) {
                query.setFirstResult(page * pageSize);
            }
        }
        
        return query.list();
    }
    
    private List processCriteria() {
        if (pageSize != null) {
            criteria.setMaxResults(pageSize + 1);
            if (page != null) {
                criteria.setFirstResult(page * pageSize);
            }
        }
        
        return criteria.list();
    }

    /**
     * Return result of condition if there are results after this list.
     * 
     * @return <code>true</code> if the list of results has a forward page 
     * <code>false</code> otherwise
     */
    public boolean hasNextPage() {
        return results.size() > pageSize;
    }

    /**
     * Return result of condition if there are results before this list.
     * 
     * @return <code>true</code> if the list of results has a backward page 
     * <code>false</code> otherwise
     */
    public boolean hasPreviousPage() {
        return page > 0;
    }

    public List getResults() {
        return hasNextPage()? results.subList(0, pageSize - 1): results;
    }
}
