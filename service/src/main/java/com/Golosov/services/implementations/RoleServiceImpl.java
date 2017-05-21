package com.Golosov.services.implementations;

import com.Golosov.dao.interfaces.RoleDao;
import com.Golosov.entities.Role;
import com.Golosov.exceptions.DaoException;
import com.Golosov.services.dto.converters.Converter;
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
            logger.info("Role" + role + "successfully saved!");
        } catch (DaoException dao) {
            logger.error("Error was thrown in service: " + dao);
            throw new ServiceException();
        }
        return role.getId();
    }

    @Override
    public void delete(long id) {
        try {
            roleDao.delete(id);
        } catch (DaoException dao) {
            logger.error("Error was thrown in service: " + dao);
            throw new ServiceException();
        }
    }

    @Override
    public void update(RoleDto roleDto) {
        Role role = Converter.roleDtoToRoleEntityConverter(roleDto);
        try {
            roleDao.update(role);
        } catch (DaoException dao) {
            logger.error("Error was thrown in service: " + dao);
            throw new ServiceException();
        }
    }

    @Override
    public List<RoleDto> getAll() {
        List<RoleDto> roleDtos = new ArrayList<>();
        try {
            List<Role> roles = roleDao.getAll();
            roles.forEach(role -> {
                RoleDto roleDto = Converter.roleEntityToRoleDtoConverter(role);
                roleDtos.add(roleDto);
            });
        } catch (DaoException dao) {
            logger.error("Error was thrown in service: " + dao);
            throw new ServiceException();
        }
        return roleDtos;
    }

    @Override
    public RoleDto get(long id) {
        RoleDto roleDto;
        try {
            Role role = roleDao.getById(id);
            roleDto = Converter.roleEntityToRoleDtoConverter(role);
        } catch (DaoException dao) {
            logger.error("Error was thrown in service: " + dao);
            throw new ServiceException();
        }
        return roleDto;
    }
}
