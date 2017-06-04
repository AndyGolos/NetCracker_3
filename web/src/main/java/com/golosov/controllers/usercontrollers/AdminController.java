package com.golosov.controllers.usercontrollers;

import com.golosov.controllers.abstracts.BaseController;
import com.golosov.controllers.responses.SuccessResponse;
import com.golosov.services.dto.dto.RoleDto;
import com.golosov.services.dto.dto.UserDto;
import com.golosov.services.interfaces.CardService;
import com.golosov.services.interfaces.RoleService;
import com.golosov.services.interfaces.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * Created by Андрей on 18.05.2017.
 */
@RestController
@RequestMapping("/api")
public class AdminController extends BaseController {

    private final Logger logger = Logger.getLogger(AdminController.class);

    private CardService cardService;
    private UserService userService;
    private RoleService roleService;

    @Autowired
    public AdminController(
            CardService cardService,
            UserService userService,
            RoleService roleService) {
        this.cardService = cardService;
        this.userService = userService;
        this.roleService = roleService;
    }

    @RequestMapping(value = "/cards/unblockCard/{id}", method = RequestMethod.PUT)
    public ResponseEntity<SuccessResponse> unblockCard(@PathVariable long id) {
        cardService.unblockCard(id);
        logger.debug("Successfully unblocked!");
        return new ResponseEntity<>(new SuccessResponse(id, HttpStatus.OK.toString()), HttpStatus.OK);
    }

    @RequestMapping(value = "/registerAdmin", method = RequestMethod.POST)
    public ResponseEntity<SuccessResponse> registerAdmin(@RequestBody UserDto userDto) {
        long id = userService.saveAdmin(userDto);
        logger.debug("Admin successfully registered!");
        return new ResponseEntity<>(new SuccessResponse(id, HttpStatus.CREATED.toString()), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/users/{userId}/roles", method = RequestMethod.GET)
    public ResponseEntity<Set<RoleDto>> getUserRoles(@PathVariable long userId) {
        Set<RoleDto> roles = roleService.userRoles(userId);
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

}
