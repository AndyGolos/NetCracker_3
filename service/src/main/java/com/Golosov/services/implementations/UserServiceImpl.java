package com.Golosov.services.implementations;

import com.Golosov.dao.interfaces.UserDao;
import com.Golosov.entities.Role;
import com.Golosov.entities.User;
import com.Golosov.exceptions.DaoException;
import com.Golosov.services.dto.converters.Converter;
import com.Golosov.services.dto.dto.UserDto;
import com.Golosov.services.exceptions.ServiceException;
import com.Golosov.services.interfaces.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Андрей on 17.05.2017.
 */
@Service
@Transactional(rollbackFor = DaoException.class)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    private static Logger logger = Logger.getLogger(UserServiceImpl.class);

    @Override
    public UserDto findUserInfo(UserDto userDto) {
        UserDto currentUserDto;
        User user = Converter.userDtoToTypeEntityConverter(userDto);
        try {
            User currentUser = userDao.getByEmail(user.getEmail());
            currentUserDto = Converter.userEntityToUserDtoConverter(currentUser);
        } catch (DaoException dao) {
            logger.error("Error was thrown in service: " + dao);
            throw new ServiceException();
        }
        return currentUserDto;
    }

    @Override
    public long save(UserDto userDto) {
        User user = Converter.userDtoToTypeEntityConverter(userDto);
        try {
            Set<Role> roles = new HashSet<>();
            Role role = new Role();
            role.setId(1);
            roles.add(role);
            user.setRoles(roles);
            return userDao.save(user);
        } catch (DaoException dao) {
            logger.error("Error was thrown in service: " + dao);
            throw new ServiceException();
        }
    }

    @Override
    public void delete(long id) {
        try {
            userDao.delete(id);
        } catch (DaoException dao) {
            logger.error("Error was thrown in service: " + dao);
            throw new ServiceException();
        }
    }

    @Override
    public void update(UserDto userDto) {
        User user = Converter.userDtoToTypeEntityConverter(userDto);
        try {
            userDao.update(user);
        } catch (DaoException dao) {
            logger.error("Error was thrown in service: " + dao);
            throw new ServiceException();
        }
    }

    @Override
    public List<UserDto> getAll() {
        List<UserDto> userDtos = new ArrayList<>();
        try {
            List<User> users = userDao.getAll();
            users.forEach(user -> {
                UserDto userDto = Converter.userEntityToUserDtoConverter(user);
                userDtos.add(userDto);
            });
        } catch (DaoException dao) {
            logger.error("Error was thrown in service: " + dao);
            throw new ServiceException();
        }
        return userDtos;
    }

    @Override
    public UserDto get(long id) {
        UserDto userDto;
        try {
            User user = userDao.getById(id);
            userDto = Converter.userEntityToUserDtoConverter(user);
        } catch (DaoException dao) {
            logger.error("Error was thrown in service: " + dao);
            throw new ServiceException();
        }
        return userDto;
    }
}
