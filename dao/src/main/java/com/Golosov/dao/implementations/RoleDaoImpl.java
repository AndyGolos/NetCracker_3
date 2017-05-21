package com.Golosov.dao.implementations;

import com.Golosov.dao.interfaces.RoleDao;
import com.Golosov.entities.Role;
import com.Golosov.entities.User;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * Created by Андрей on 16.05.2017.
 */
@Repository
public class RoleDaoImpl extends AbstractDao<Role> implements RoleDao {

    private static final String FROM_ROLE = "from Role";

    public RoleDaoImpl() {
        super(Role.class, FROM_ROLE);
    }

    public Set<Role> getRolesByUserId(long id) {
        User user = entityManager.find(User.class,id);
        return user.getRoles();
    }
}
