package com.golosov.controllers.usercontrollers;

import com.golosov.entities.User;
import com.golosov.services.dto.dto.BaseDto;
import com.golosov.services.dto.dto.UserDto;
import com.golosov.services.interfaces.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Андрей on 29.05.2017.
 */
@RestController
public class AllAvailableController {

    private final Logger logger = Logger.getLogger(AllAvailableController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto) {
        long id = userService.save(userDto);
        logger.debug("User was successfully registered");
        return new ResponseEntity<>(new UserDto(id, HttpStatus.CREATED.toString()), HttpStatus.CREATED);
    }
}
