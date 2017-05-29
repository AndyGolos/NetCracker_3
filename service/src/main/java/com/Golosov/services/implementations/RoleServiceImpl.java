package com.Golosov.services.implementations;

import com.Golosov.dao.interfaces.RoleDao;
import com.Golosov.entities.Role;
import com.Golosov.exceptions.DaoException;
import com.Golosov.services.dto.Converter;
import com.Golosov.services.dto.dto.RoleDto;
import com.Golosov.services.exceptions.ServiceException;
import com.Golosov.services.interfaces.RoleService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Андрей on 17.05.2017.
 */
@Service
@Transactional(rollbackFor = DaoException.class)
public class RoleServiceImpl implements RoleService {

    private static Logger logger = Logger.getLogger(RoleServiceImpl.class);

    @Autowired
    private RoleDao roleDao;

    @Override
    public long save(RoleDto roleDto) {
        Role role = Converter.roleDtoToRoleEntityConverter(roleDto);
        try {
            roleDao.save(role);
            logger.debug("Role" + role + "successfully saved!");
        } catch (DaoException e) {
            logger.error("Error was thrown in role service method role save: " + e);
            throw new ServiceException(e);
        }
        return role.getId();
    }

    @Override
    public void delete(long id) {
        try {
            roleDao.delete(id);
            logger.debug("Role by id: " + id + " successfully deleted!");
        } catch (DaoException e) {
            logger.error("Error was thrown in role service method role delete: " + e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void update(RoleDto roleDto) {
        Role role = Converter.roleDtoToRoleEntityConverter(roleDto);
        try {
            roleDao.update(role);
            logger.debug("Role: " + roleDto + " successfully updated!");
        } catch (DaoException e) {
            logger.error("Error was thrown in role service method role update: " + e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<RoleDto> getAll() {
        List<RoleDto> roleDtos = new ArrayList<>();
        try {
            List<Role> roles = roleDao.getAll();
            logger.debug("All roles successfully found!");
            roles.forEach(role -> {
                RoleDto roleDto = Converter.roleEntityToRoleDtoConverter(role);
                roleDtos.add(roleDto);
            });
        } catch (DaoException e) {
            logger.error("Error was thrown in role service method role getAll: " + e);
            throw new ServiceException(e);
        }
        return roleDtos;
    }

    @Override
    public RoleDto get(long id) {
        RoleDto roleDto;
        try {
            Role role = roleDao.getById(id);
            logger.debug("Role by id: " + id + " successfully found!");
            roleDto = Converter.roleEntityToRoleDtoConverter(role);
        } catch (DaoException e) {
            logger.error("Error was thrown in role service method role get: " + e);
            throw new ServiceException(e);
        }
        return roleDto;
    }
}
