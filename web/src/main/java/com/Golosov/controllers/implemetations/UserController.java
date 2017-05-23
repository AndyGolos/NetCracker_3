package com.Golosov.controllers.implemetations;

import com.Golosov.controllers.abstracts.AbstractCrudController;
import com.Golosov.services.dto.dto.UserDto;
import com.Golosov.services.interfaces.BaseService;
import com.Golosov.services.interfaces.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Андрей on 23.05.2017.
 */
@RestController
@RequestMapping("/users")
public class UserController extends AbstractCrudController<UserDto> {

    private final Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<Long> saveUser(@RequestBody UserDto userDto) {
        return save(userDto);
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public ResponseEntity updateUser(@RequestBody UserDto userDto) {
        return update(userDto);
    }

    @Override
    protected BaseService<UserDto> getService() {
        return userService;
    }
}
