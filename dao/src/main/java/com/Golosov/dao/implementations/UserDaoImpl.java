package com.Golosov.dao.implementations;

import com.Golosov.dao.interfaces.UserDao;
import com.Golosov.entities.User;
import com.Golosov.exceptions.DaoException;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Repository;

/**
 * Created by Андрей on 16.05.2017.
 */
@Repository
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
            user = (User) entityManager
                    .createQuery(FROM_USER_WHERE_EMAIL)
                    .setParameter("email", email)
                    .getResultList()
                    .get(0);
            logger.info("User: " + user + " successfully found!");
        } catch (HibernateException e) {
            logger.error("Error was thrown in UserDaoImpl method getByEmail: " + e);
            throw new DaoException(e);
        }
        return user;
    }
}
