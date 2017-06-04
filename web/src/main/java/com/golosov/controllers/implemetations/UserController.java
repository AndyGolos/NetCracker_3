package com.golosov.controllers.implemetations;

import com.golosov.controllers.abstracts.AbstractCrudController;
import com.golosov.controllers.responses.SuccessResponse;
import com.golosov.services.dto.dto.UserDto;
import com.golosov.services.interfaces.BaseService;
import com.golosov.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Андрей on 23.05.2017.
 */
@RestController
@RequestMapping("/api/users")
public class UserController extends AbstractCrudController<UserDto> {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<SuccessResponse> saveUser(@RequestBody UserDto userDto) {
        return save(userDto);
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateUser(@RequestBody UserDto userDto, @PathVariable long id) {
        return update(userDto, id);
    }

    @Override
    protected BaseService<UserDto> getService() {
        return userService;
    }
}
