package com.Golosov.controllers;

import com.Golosov.entities.User;
import com.Golosov.services.dto.dto.UserDto;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Андрей on 18.05.2017.
 */
@RestController
@RequestMapping("/")
public class MainController {


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public User login(@ModelAttribute UserDto userDto){
        return null;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public void logout(){
    }
}
