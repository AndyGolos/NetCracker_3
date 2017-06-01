package com.golosov.dao.interfaces;

import com.golosov.entities.Role;

import java.util.Set;

/**
 * Created by Андрей on 16.05.2017.
 */
public interface RoleDao extends BaseDao<Role> {

    Set<Role> getRolesByUserId(long id);
}
