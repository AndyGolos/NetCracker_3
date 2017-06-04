package com.golosov.dao.implementations;

import com.golosov.dao.interfaces.RoleDao;
import com.golosov.entities.Role;
import com.golosov.entities.User;
import com.golosov.exceptions.DaoException;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * Created by Андрей on 16.05.2017.
 */
@Repository
public class RoleDaoImpl extends AbstractDao<Role> implements RoleDao {

    private static Logger logger = Logger.getLogger(RoleDaoImpl.class);

    private static final String FROM_ROLE = "from Role";

    public RoleDaoImpl() {
        super(Role.class, FROM_ROLE);
    }
    //TODO
    public Set<Role> getRolesByUserId(long id) {
        Set<Role> roles = null;
        try {
            User user = entityManager.find(User.class, id);
            if (user != null) {
                roles = user.getRoles();
                logger.debug("User roles with id: " + id + " successfully found!");
            } else {
                throw new IllegalArgumentException("Inocorrect data entered!");
            }
        } catch (HibernateException e) {
            logger.error("Error was thrown in RoleDaoImpl method getRolesByUserId: " + e);
            throw new DaoException(e);
        }
        return roles;
    }
}
