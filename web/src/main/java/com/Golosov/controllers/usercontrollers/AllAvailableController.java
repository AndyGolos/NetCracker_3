package com.Golosov.controllers.usercontrollers;

import com.Golosov.services.dto.dto.UserDto;
import com.Golosov.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Андрей on 29.05.2017.
 */
@Controller
public class AllAvailableController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<Long> registerUser(@RequestBody UserDto userDto){
        long id = userService.save(userDto);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }
}
