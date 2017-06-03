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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * Created by Андрей on 18.05.2017.
 */
@RestController
@RequestMapping("/api")
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

    //работает
    @RequestMapping(value = "/users/profile", method = RequestMethod.GET)
    public ResponseEntity<UserDto> getUser() {
        long userId = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        UserDto user = userService.get(userId);
        logger.debug("User info successfully found!");
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    //Работает
    @RequestMapping(value = "/cards/blockCard/{id}", method = RequestMethod.PUT)
    public ResponseEntity<CardDto> blockCard(@PathVariable long id) {
        //TODO только для пользователя.
        cardService.blockCard(id);
        logger.debug("Card successfully blocked!");
        return new ResponseEntity<>(new CardDto(id, HttpStatus.OK.toString()), HttpStatus.OK);
    }

    //Работает
    @RequestMapping(value = "/bills/replenishBill", method = RequestMethod.POST)
    public ResponseEntity<BillDto> replenishBill(@RequestBody BillDto billDto) {
        billService.replenishBill(billDto);
        logger.debug("Bill successfully replenished!");
        return new ResponseEntity<>(new BillDto(billDto.getId(), HttpStatus.OK.toString()), HttpStatus.OK);
    }

    //Работает
    //TODO добавить response entity?
    @RequestMapping(value = "/cards/transferMoney", method = RequestMethod.POST)
    public ResponseEntity transferMoney(@RequestBody TransferDto transferDto) {
        //TODO только для пользователя.
        cardService.transferMoney(transferDto);
        logger.debug("Money successfully transfered!");
        return new ResponseEntity(HttpStatus.OK);
    }

    //Работает
    @RequestMapping(value = "/cards/createCard", method = RequestMethod.POST)
    public ResponseEntity<CardDto> createCard(@RequestBody CardDto cardDto) {
        //TODO только для пользователя.
        long cardId = cardService.save(cardDto);
        logger.debug("Card successfully created!");
        return new ResponseEntity<>(new CardDto(cardId, HttpStatus.CREATED.toString()), HttpStatus.CREATED);
    }

    //Работает
    @RequestMapping(value = "/cards/{cardId}/histories", method = RequestMethod.GET)
    public ResponseEntity<Set<HistoryDto>> getHistoriesOfCard(@PathVariable long cardId) {
        //TODO только для пользователя.
        Set<HistoryDto> histories = historyService.findCardHistory(cardId);
        logger.debug("Histories successfully found!");
        return new ResponseEntity<>(histories, HttpStatus.OK);
    }
}
