package com.golosov.dao.implementations;

import com.golosov.dao.interfaces.UserDao;
import com.golosov.entities.User;
import com.golosov.exceptions.DaoException;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Андрей on 16.05.2017.
 */
@Repository
@SuppressWarnings("unchecked")
public class UserDaoImpl extends AbstractDao<User> implements UserDao {

    private static Logger logger = Logger.getLogger(UserDaoImpl.class);

    private static final String FROM_USER_WHERE_EMAIL = "from User where email=:email";
    private static final String FROM_USER = "from User";

    public UserDaoImpl() {
        super(User.class, FROM_USER);
    }

    public User getByEmail(String email) {
        User user;
        try {
            List<User> users = entityManager
                    .createQuery(FROM_USER_WHERE_EMAIL)
                    .setParameter("email", email)
                    .getResultList();
            if (users.isEmpty()) {
                return null;
            } else {
                user = users.get(0);
            }
            logger.debug("User: " + user + " successfully found!");
        } catch (HibernateException e) {
            logger.error("Error was thrown in UserDaoImpl method getByEmail: " + e);
            throw new DaoException(e);
        }
        return user;
    }
}
