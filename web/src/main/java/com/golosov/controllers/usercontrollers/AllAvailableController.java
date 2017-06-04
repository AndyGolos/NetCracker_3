package com.golosov.controllers.usercontrollers;

import com.golosov.controllers.abstracts.BaseController;
import com.golosov.security.service.LoginService;
import com.golosov.controllers.responses.SuccessResponse;
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
public class AllAvailableController extends BaseController {

    private final Logger logger = Logger.getLogger(AllAvailableController.class);

    private UserService userService;
    private LoginService loginService;
    
    @Autowired
    public AllAvailableController(
            UserService userService,
            LoginService loginService
    ) {
        this.userService = userService;
        this.loginService = loginService;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<SuccessResponse> registerUser(@RequestBody UserDto userDto) {
        long id = userService.save(userDto);
        logger.debug("User was successfully registered");
        return new ResponseEntity<>(new SuccessResponse(id, HttpStatus.CREATED.toString()), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<SuccessResponse> loginUser(@RequestBody UserDto userDto) {
        long id = userService.login(userDto);
        loginService.authenticate(userDto.getEmail(), userDto.getPassword());
        logger.debug("User was successfully signed in!");
        return new ResponseEntity<>(new SuccessResponse(id, HttpStatus.OK.toString()), HttpStatus.OK);
    }
}
