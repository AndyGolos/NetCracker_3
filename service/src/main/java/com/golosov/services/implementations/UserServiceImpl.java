package com.golosov.services.implementations;

import com.golosov.dao.interfaces.UserDao;
import com.golosov.entities.Role;
import com.golosov.entities.User;
import com.golosov.exceptions.DaoException;
import com.golosov.services.dto.Converter;
import com.golosov.services.dto.dto.UserDto;
import com.golosov.services.exceptions.ExistUserException;
import com.golosov.services.exceptions.IncorrectPasswordException;
import com.golosov.services.exceptions.NotFoundException;
import com.golosov.services.exceptions.ServiceException;
import com.golosov.services.interfaces.UserService;
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
            if (currentUser == null) {
                throw new NotFoundException("User not found!");
            }
            logger.debug("User by email: " + user.getEmail() + " successfully found!");
            currentUserDto = Converter.userEntityToUserDtoConverter(currentUser);
        } catch (DaoException e) {
            logger.error("Error was thrown in UserServiceImpl method findUserInfo: " + e);
            throw new ServiceException(e);
        }
        return currentUserDto;
    }

    @Override
    public long save(UserDto userDto) {
        User currentUser = Converter.userDtoToTypeEntityConverter(userDto);
        try {
            User user = userDao.getByEmail(currentUser.getEmail());
            if (user != null) {
                throw new ExistUserException("Such user is already exists!");
            }
            Set<Role> roles = new HashSet<>();
            roles.add(new Role(1));
            currentUser.setRoles(roles);
            currentUser.setRegistration(LocalDate.now());
            userDao.save(currentUser);
            logger.debug("User: " + currentUser + " successfully saved!");
        } catch (DaoException e) {
            logger.error("Error was thrown in UserServiceImpl method save: " + e);
            throw new ServiceException(e);
        }
        return currentUser.getId();
    }

    @Override
    public void delete(long id) {
        try {
            User user = userDao.getById(id);
            if (user == null) {
                throw new NotFoundException("User not found!");
            }
            userDao.delete(id);
            logger.debug("User by id: " + id + " successfully deleted!");
        } catch (DaoException e) {
            logger.error("Error was thrown in  UserServiceImpl method delete: " + e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void update(UserDto userDto, long id) {
        User currentUser = Converter.userDtoToTypeEntityConverter(userDto);
        try {
            User user = userDao.getById(id);
            if (user == null) {
                throw new NotFoundException("User not found!");
            }
            currentUser.setId(id);
            userDao.update(currentUser);
            logger.debug("User: " + currentUser + " successfully updated!");
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
            logger.debug("All users successfully found!");
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
            if (user == null) {
                throw new NotFoundException("User not found!");
            }
            logger.debug("User by id: " + id + " successfully found!");
            userDto = Converter.userEntityToUserDtoConverter(user);
        } catch (DaoException e) {
            logger.error("Error was thrown in UserServiceImpl method get: " + e);
            throw new ServiceException(e);
        }
        return userDto;
    }

    @Override
    public long login(UserDto userDto) {
        User user = userDao.getByEmail(userDto.getEmail());
        if (user == null) {
            throw new NotFoundException("User not found!");
        } else if (!user.getPassword().equals(userDto.getPassword())) {
            throw new IncorrectPasswordException("Incorrect password entered!");
        } else {
            return user.getId();
        }
    }
}
