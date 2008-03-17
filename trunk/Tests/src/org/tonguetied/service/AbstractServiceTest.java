package org.tonguetied.service;

import java.sql.Connection;

import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;
import org.springframework.test.annotation.AbstractAnnotationAwareTransactionalTests;
import org.tonguetied.dao.DaoRepository;
import org.tonguetied.utils.DBUtils;


/**
 * This is the base test class for service layer tests. This class uses the test 
 * configuration files. The configuration files used are:
 * <ul>
 *   <li>trialink-tests.xml</li>
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
    private DaoRepository daoRepository;
    
    private static final String BEAN_DATASOURCE = "dataSource";
    protected static final Logger log = 
        Logger.getLogger(AbstractServiceTest.class);

    static {
        synchronized("startDb") {
            try {
                DBUtils.startDatabase();
            }
            catch (Exception e) {
                log.error(e);
            }
        }
    }
    
    /* (non-Javadoc)
     * @see org.springframework.test.AbstractTransactionalSpringContextTests#onSetUpBeforeTransaction()
     */
    @Override
    protected void onSetUpBeforeTransaction() throws Exception {
        // ensure the database is clean before each test
        DriverManagerDataSource ds = 
            (DriverManagerDataSource) applicationContext.getBean(BEAN_DATASOURCE);
        Connection connection = DataSourceUtils.getConnection(ds);
        
        if (recreateSchema()) {
            DBUtils.cleanTables(connection);
        }
    }

    /* (non-Javadoc)
     * @see org.springframework.test.AbstractDependencyInjectionSpringContextTests#getConfigLocations()
     */
    @Override
    protected String[] getConfigLocations()
    {
        return new String[] {"classpath:/test-application-context.xml"}; 
    }
    
    /**
     * @return the daoRepository
     */
    public DaoRepository getDaoRepository()
    {
        return daoRepository;
    }

    /**
     * Injected by Spring.
     * 
     * @param daoRepository the daoRepository to set
     */
    public void setDaoRepository(DaoRepository daoRepository)
    {
        this.daoRepository = daoRepository;
    }
    
    protected static boolean recreateSchema() {
        return true;
    }
}
