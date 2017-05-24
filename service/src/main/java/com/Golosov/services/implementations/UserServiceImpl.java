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

import java.time.LocalDate;
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

    private static Logger logger = Logger.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Override
    public UserDto findUserInfo(UserDto userDto) {
        UserDto currentUserDto;
        User user = Converter.userDtoToTypeEntityConverter(userDto);
        try {
            User currentUser = userDao.getByEmail(user.getEmail());
            logger.info("User by email: " + user.getEmail() + " successfully found!");
            currentUserDto = Converter.userEntityToUserDtoConverter(currentUser);
        } catch (DaoException e) {
            logger.error("Error was thrown in UserServiceImpl method findUserInfo: " + e);
            throw new ServiceException(e);
        }
        return currentUserDto;
    }

    @Override
    public long save(UserDto userDto) {
        User user = Converter.userDtoToTypeEntityConverter(userDto);
        try {
            Set<Role> roles = new HashSet<>();
            //TODO
            Role role = new Role();
            role.setId(1);
            roles.add(role);
            user.setRoles(roles);
            user.setRegistration(LocalDate.now());
            userDao.save(user);
            logger.info("User: " + user + " successfully saved!");
        } catch (DaoException e) {
            logger.error("Error was thrown in UserServiceImpl method save: " + e);
            throw new ServiceException(e);
        }
        return user.getId();
    }

    @Override
    public void delete(long id) {
        try {
            userDao.delete(id);
            logger.info("User by id: " + id + " successfully deleted!");
        } catch (DaoException e) {
            logger.error("Error was thrown in  UserServiceImpl method delete: " + e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void update(UserDto userDto) {
        User user = Converter.userDtoToTypeEntityConverter(userDto);
        try {
            userDao.update(user);
            logger.info("User: " + user + " successfully updated!");
        } catch (DaoException e) {
            logger.error("Error was thrown in  UserServiceImpl method update: " + e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<UserDto> getAll() {
        List<UserDto> userDtos = new ArrayList<>();
        try {
            List<User> users = userDao.getAll();
            logger.info("All users successfully found!");
            users.forEach(user -> {
                UserDto userDto = Converter.userEntityToUserDtoConverter(user);
                userDtos.add(userDto);
            });
        } catch (DaoException e) {
            logger.error("Error was thrown in UserServiceImpl method getAll: " + e);
            throw new ServiceException(e);
        }
        return userDtos;
    }

    @Override
    public UserDto get(long id) {
        UserDto userDto;
        try {
            User user = userDao.getById(id);
            logger.info("User by id: " + id + " successfully found!");
            userDto = Converter.userEntityToUserDtoConverter(user);
        } catch (DaoException e) {
            logger.error("Error was thrown in UserServiceImpl method get: " + e);
            throw new ServiceException(e);
        }
        return userDto;
    }
}
