package org.tonguetied.service;

import java.util.List;

import org.tonguetied.dao.DaoRepository;
import org.tonguetied.domain.User;


/**
 * @author bsion
 *
 */
public class UserServiceImpl implements UserService {
    private DaoRepository daoRepository;

    /* (non-Javadoc)
     * @see org.tonguetied.service.UserService#getUser(java.lang.String)
     */
    public User getUser(final String username) {
        return daoRepository.getUser(username);
    }

    /* (non-Javadoc)
     * @see org.tonguetied.service.UserService#getUser(java.lang.Long)
     */
    public User getUser(final Long id) {
        return daoRepository.getUser(id);
    }

    /* (non-Javadoc)
     * @see org.tonguetied.service.UserService#getUsers()
     */
    public List<User> getUsers() {
        return daoRepository.getUsers();
    }

    /* (non-Javadoc)
     * @see org.tonguetied.service.UserService#saveOrUpdate(org.tonguetied.domain.User)
     */
    public void saveOrUpdate(User user) {
        daoRepository.saveOrUpdate(user);
    }

    public void setDaoRepository(DaoRepository daoRepository) {
        this.daoRepository = daoRepository;
    }
}
