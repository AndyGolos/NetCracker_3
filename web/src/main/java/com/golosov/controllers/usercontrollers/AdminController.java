package com.golosov.controllers.usercontrollers;

import com.golosov.services.dto.dto.CardDto;
import com.golosov.services.dto.dto.UserDto;
import com.golosov.services.interfaces.CardService;
import com.golosov.services.interfaces.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * Created by Андрей on 18.05.2017.
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final Logger logger = Logger.getLogger(AdminController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private CardService cardService;

    //работает
    @RequestMapping(value = "/users/", method = RequestMethod.GET)
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAll();
        logger.debug("All users successfully found!");
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    //TODO одинаковые методы в admin и client
    //работает
    @RequestMapping(value = "/cards/{userId}", method = RequestMethod.GET)
    public ResponseEntity<Set<CardDto>> getUsersCards(@PathVariable long userId) {
        Set<CardDto> usersCards = cardService.findUsersCards(userId);
        logger.debug("All users successfully found!");
        return new ResponseEntity<>(usersCards, HttpStatus.OK);
    }

    //TODO PUT???
    //работает
    @RequestMapping(value = "/unblockCard/", method = RequestMethod.POST)
    public ResponseEntity unblockCard(@RequestBody CardDto cardDto) {
        cardService.unblockCard(cardDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    //работает
    @RequestMapping(value = "/deleteCard/{cardId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteCard(@PathVariable long cardId) {
        cardService.delete(cardId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
