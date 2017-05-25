package com.Golosov.controllers.usercontrollers;

import com.Golosov.services.dto.dto.CardDto;
import com.Golosov.services.dto.dto.TypeDto;
import com.Golosov.services.dto.dto.UserDto;
import com.Golosov.services.interfaces.CardService;
import com.Golosov.services.interfaces.TypeService;
import com.Golosov.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
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

    @Autowired
    private UserService userService;
    @Autowired
    private CardService cardService;

    @RequestMapping(value = "/users/", method = RequestMethod.GET)
    public ResponseEntity<List<UserDto>> getAllUsers(){
        List<UserDto> users = userService.getAll();
        return new ResponseEntity<List<UserDto>>(users, HttpStatus.OK);
    }

    @RequestMapping(value = "/cards/{userId}", method = RequestMethod.GET)
    public ResponseEntity<Set<CardDto>> getUsersCards(@PathVariable long userId){
        Set<CardDto> usersCards = cardService.findUsersCards(userId);
        return new ResponseEntity<Set<CardDto>>(usersCards,HttpStatus.OK);
    }

    @RequestMapping(value = "/unblockCard", method = RequestMethod.POST)
    public ResponseEntity unblockCard(@RequestBody CardDto cardDto){
        cardService.unblockCard(cardDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/deleteCard/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteCard(@PathVariable long cardId){
        cardService.delete(cardId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
