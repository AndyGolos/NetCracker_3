package com.golosov.controllers.usercontrollers;

import com.golosov.security.details.CustomUserDetails;
import com.golosov.services.dto.dto.*;
import com.golosov.services.interfaces.BillService;
import com.golosov.services.interfaces.CardService;
import com.golosov.services.interfaces.HistoryService;
import com.golosov.services.interfaces.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * Created by Андрей on 18.05.2017.
 */
@RestController
@RequestMapping("/client")
public class ClientController {

    private final Logger logger = Logger.getLogger(ClientController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private CardService cardService;
    @Autowired
    private BillService billService;
    @Autowired
    private HistoryService historyService;


    /*@RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<UserDto> getUser(@PathVariable long id) {
        UserDto user = userService.get(id);
        logger.debug("User successfully found!");
        return new ResponseEntity<>(user, HttpStatus.OK);
    }*/

    //работает
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<UserDto> getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails authorizedUser = (CustomUserDetails) authentication.getPrincipal();
        UserDto user = userService.get(authorizedUser.getId());
        logger.debug("User info successfully found!");
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    //Работает
    @RequestMapping(value = "/blockCard/", method = RequestMethod.POST)
    public ResponseEntity blockCard(@RequestBody CardDto cardDto) {
        cardService.blockCard(cardDto);
        logger.debug("Card successfully blocked!");
        return new ResponseEntity(HttpStatus.OK);
    }

    //Работает
    @RequestMapping(value = "/replenishBill/", method = RequestMethod.POST)
    public ResponseEntity replenishBill(@RequestBody BillDto billDto) {
        billService.replenishBill(billDto);
        logger.debug("Bill successfully replenished!");
        return new ResponseEntity(HttpStatus.OK);
    }

    //Не работает в постмане. Жалуется на Required long parameter 'fromCardId' is not present
    @RequestMapping(value = "/transferMoney/", method = RequestMethod.POST)
    public ResponseEntity transferMoney(@RequestBody TransferDto transferDto) {
        cardService.transferMoney(transferDto);
        logger.debug("Money successfully transfered!");
        return new ResponseEntity(HttpStatus.OK);
    }

    //Работает
    @RequestMapping(value = "/createCard/", method = RequestMethod.POST)
    public ResponseEntity<CardDto> createCard(@RequestBody CardDto cardDto) {
        long cardId = cardService.save(cardDto);
        logger.debug("Card successfully created!");
        return new ResponseEntity<>(new CardDto(cardId, HttpStatus.CREATED.toString()), HttpStatus.CREATED);
    }

    //Работает
    @RequestMapping(value = "/histories/{cardId}", method = RequestMethod.GET)
    public ResponseEntity<Set<HistoryDto>> getHistoriesOfCard(@PathVariable long cardId) {
        Set<HistoryDto> histories = historyService.findCardHistory(cardId);
        logger.debug("Histories successfully found!");
        return new ResponseEntity<>(histories, HttpStatus.OK);
    }
}
