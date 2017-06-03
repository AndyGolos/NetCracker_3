package com.golosov.controllers.usercontrollers;

import com.golosov.security.service.LoginService;
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
@RequestMapping("/api")
public class AllAvailableController {

    private final Logger logger = Logger.getLogger(AllAvailableController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto) {
        long id = userService.save(userDto);
        logger.debug("User was successfully registered");
        return new ResponseEntity<>(new UserDto(id, HttpStatus.CREATED.toString()), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<UserDto> loginUser(@RequestBody UserDto userDto) {
        long id = userService.login(userDto);
        loginService.authenticate(userDto.getEmail(), userDto.getPassword());
        logger.debug("User was successfully signed in!");
        return new ResponseEntity<>(new UserDto(id, HttpStatus.OK.toString()), HttpStatus.OK);
    }
}
