package com.golosov.services.implementations;

import com.golosov.dao.interfaces.RoleDao;
import com.golosov.entities.Role;
import com.golosov.exceptions.DaoException;
import com.golosov.services.dto.Converter;
import com.golosov.services.dto.dto.RoleDto;
import com.golosov.services.exceptions.NotFoundException;
import com.golosov.services.exceptions.ServiceException;
import com.golosov.services.interfaces.RoleService;
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
            Role role = roleDao.getById(id);
            if (role == null) {
                throw new NotFoundException("Role not found!");
            }
            roleDao.delete(id);
            logger.debug("Role by id: " + id + " successfully deleted!");
        } catch (DaoException e) {
            logger.error("Error was thrown in role service method role delete: " + e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void update(RoleDto roleDto, long id) {
        Role currentRole = Converter.roleDtoToRoleEntityConverter(roleDto);
        try {
            Role role = roleDao.getById(id);
            if (role == null) {
                throw new NotFoundException("Role not found!");
            }
            currentRole.setId(id);
            roleDao.update(currentRole);
            logger.debug("Role: " + currentRole + " successfully updated!");
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
            if (role == null) {
                throw new NotFoundException("Role not found!");
            }
            logger.debug("Role by id: " + id + " successfully found!");
            roleDto = Converter.roleEntityToRoleDtoConverter(role);
        } catch (DaoException e) {
            logger.error("Error was thrown in role service method role get: " + e);
            throw new ServiceException(e);
        }
        return roleDto;
    }
}
