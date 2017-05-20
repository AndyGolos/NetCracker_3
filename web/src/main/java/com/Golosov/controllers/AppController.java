package com.Golosov.controllers;

import com.Golosov.builders.TypeBuilder;
import com.Golosov.builders.UserBuilder;
import com.Golosov.dao.interfaces.UserDao;
import com.Golosov.entities.Role;
import com.Golosov.entities.Type;
import com.Golosov.entities.User;
import com.Golosov.services.interfaces.TypeService;
import com.Golosov.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Андрей on 16.05.2017.
 */
@Controller
public class AppController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserService userService;
    @Autowired
    private TypeService typeService;

    @RequestMapping("/test")
    public String test(){

        User actualUser = new UserBuilder
                .UserEntityBuilder()
                .name("Andy")
                .surname("Golosov")
                .lastname("Dmitrievich")
                .email("qwerty@mail.ru")
                .password("creator")
                .dateOfBirth(LocalDate.now().minusYears(20))
                .registration(LocalDate.now())
                .build();

        /*Set<Role> roles = new HashSet<>();
        Role role = new Role();
        role.setId(1);
        roles.add(role);


        actualUser.setRoles(roles);*/


//        userDao.save(actualUser);
//        userService.save(actualUser);
        System.out.println(actualUser.getId());
        return "test";
    }

    @RequestMapping("/test2")
    public String test2(){
        Type actualType = new TypeBuilder
                .TypeEntityBuilder()
                .type("SuperCard")
                .build();
//        typeService.save(actualType);
        System.out.println(actualType.getId());
        return "test";
    }




}
