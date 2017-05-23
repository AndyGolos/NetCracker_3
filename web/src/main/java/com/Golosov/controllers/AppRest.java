package com.Golosov.controllers;

import com.Golosov.entities.Type;
import com.Golosov.entities.User;
import com.Golosov.services.dto.dto.TypeDto;
import com.Golosov.services.dto.dto.UserDto;
import com.Golosov.services.interfaces.TypeService;
import com.Golosov.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Set;

/**
 * Created by Андрей on 17.05.2017.
 */
/*@RestController*/
public class AppRest {

    /*@Autowired
    private UserService userService;

    @Autowired
    private TypeService typeService;

    @RequestMapping("/user")
    @ResponseBody
    public UserDto getUser(ModelAndView modelAndView){
        return userService.get(1);
    }

    @RequestMapping("/types")
    @ResponseBody
    public List<TypeDto> getType(){
        return typeService.getAll();
    }*/
}
