package com.golosov.controllers.usercontrollers;

import com.golosov.services.dto.dto.CardDto;
import com.golosov.services.dto.dto.UserDto;
import com.golosov.services.interfaces.CardService;
import com.golosov.services.interfaces.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * Created by Андрей on 18.05.2017.
 */
@RestController
@RequestMapping("/api")
public class AdminController {

    private final Logger logger = Logger.getLogger(AdminController.class);

    @Autowired
    private CardService cardService;

    //работает
    @RequestMapping(value = "/unblockCard/{id}", method = RequestMethod.PUT)
    public ResponseEntity<CardDto> unblockCard(@PathVariable long id) {
        cardService.unblockCard(id);
        logger.debug("Successfully unblocked!");
        return new ResponseEntity<>(new CardDto(id, HttpStatus.OK.toString()), HttpStatus.OK);
    }
}
