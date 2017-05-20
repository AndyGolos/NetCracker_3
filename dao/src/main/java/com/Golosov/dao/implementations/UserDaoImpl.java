package com.Golosov.dao.implementations;

import com.Golosov.dao.interfaces.UserDao;
import com.Golosov.entities.User;
import org.springframework.stereotype.Repository;

/**
 * Created by Андрей on 16.05.2017.
 */
@Repository
public class UserDaoImpl extends AbstractDao<User> implements UserDao {

    private static final String FROM_USER_WHERE_EMAIL = "from User where email=:email";
    private static final String FROM_USER = "from User";

    public UserDaoImpl() {
        super(User.class, FROM_USER);
    }

    public User getByEmail(String email) {
        return (User) entityManager
                .createQuery(FROM_USER_WHERE_EMAIL)
                .setParameter("email", email)
                .getResultList()
                .get(0);
    }
}
