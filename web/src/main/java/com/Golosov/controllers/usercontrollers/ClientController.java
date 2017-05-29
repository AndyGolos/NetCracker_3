package com.Golosov.controllers.usercontrollers;

import com.Golosov.services.dto.dto.BillDto;
import com.Golosov.services.dto.dto.CardDto;
import com.Golosov.services.dto.dto.HistoryDto;
import com.Golosov.services.dto.dto.UserDto;
import com.Golosov.services.interfaces.*;
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
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private UserService userService;
    @Autowired
    private CardService cardService;
    @Autowired
    private BillService billService;
    @Autowired
    private HistoryService historyService;


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<UserDto> getUser(@PathVariable long id) {
        UserDto user = userService.get(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    //TODO одинаковые методы в admin и client
    @RequestMapping(value = "/cards/{userId}", method = RequestMethod.GET)
    public ResponseEntity<Set<CardDto>> getUsersCards(@PathVariable long userId) {
        Set<CardDto> usersCards = cardService.findUsersCards(userId);
        return new ResponseEntity<>(usersCards, HttpStatus.OK);
    }

    @RequestMapping(value = "/blockCard/", method = RequestMethod.POST)
    public ResponseEntity blockCard(@RequestBody CardDto cardDto) {
        cardService.blockCard(cardDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/replenishBill/", method = RequestMethod.POST)
    public ResponseEntity replenishBill(@RequestBody BillDto billDto) {
        billService.replenishBill(billDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/transferMoney/", method = RequestMethod.POST)
    public ResponseEntity<Boolean> transferMoney(
            @RequestParam("fromCardId") long fromCardId,
            @RequestParam("fromCardPassword") String fromCardPassword,
            @RequestParam("toCardId") long toCardId,
            @RequestParam("amountOfMoney") long amountOfMoney
    ) {
        boolean transfered = cardService.transferMoney(fromCardId, fromCardPassword, toCardId, amountOfMoney);
        return new ResponseEntity<>(transfered, HttpStatus.OK);
    }

    //TODO метод еще не протещен
    @RequestMapping(value = "/createCard/", method = RequestMethod.POST)
    public ResponseEntity<Long> createCard(@RequestBody CardDto cardDto){
        long cardId = cardService.save(cardDto);
        return new ResponseEntity<>(cardId,HttpStatus.CREATED);
    }

    @RequestMapping(value = "/histories/{cardId}", method = RequestMethod.GET)
    public ResponseEntity<Set<HistoryDto>> getHistoriesOfCard(@PathVariable long cardId){
        Set<HistoryDto> histories = historyService.findCardHistory(cardId);
        return new ResponseEntity<>(histories,HttpStatus.OK);
    }
}
