package com.Golosov.dao.interfaces;

import com.Golosov.entities.User;

/**
 * Created by Андрей on 16.05.2017.
 */
public interface UserDao extends BaseDao<User> {

    User getByEmail(String email);
}
