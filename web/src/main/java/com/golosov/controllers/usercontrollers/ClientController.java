package com.golosov.controllers.usercontrollers;

import com.golosov.controllers.abstracts.BaseController;
import com.golosov.controllers.exceptions.NoAccessException;
import com.golosov.controllers.responses.SuccessResponse;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * Created by Андрей on 18.05.2017.
 */
@RestController
@RequestMapping("/api")
public class ClientController extends BaseController {

    private final Logger logger = Logger.getLogger(ClientController.class);

    private UserService userService;
    private CardService cardService;
    private BillService billService;
    private HistoryService historyService;

    @Autowired
    public ClientController(
            UserService userService,
            CardService cardService,
            BillService billService,
            HistoryService historyService
    ) {
        this.userService = userService;
        this.cardService = cardService;
        this.billService = billService;
        this.historyService = historyService;
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ResponseEntity<UserDto> getUser() {
        long userId = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        UserDto user = userService.get(userId);
        logger.debug("User info successfully found!");
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/usercards", method = RequestMethod.GET)
    public ResponseEntity<List<CardDto>> getUserCards() {
        long userId = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        List<CardDto> cards = cardService.findUsersCards(userId);
        return new ResponseEntity<>(cards, HttpStatus.OK);
    }

    @RequestMapping(value = "/blockCard/{id}", method = RequestMethod.PUT)
    public ResponseEntity<SuccessResponse> blockCard(@PathVariable long id) {
        cardAccessRights(id);
        cardService.blockCard(id);
        logger.debug("Card successfully blocked!");
        return new ResponseEntity<>(new SuccessResponse(id, HttpStatus.OK.toString()), HttpStatus.OK);
    }

    @RequestMapping(value = "/replenishBill", method = RequestMethod.POST)
    public ResponseEntity<SuccessResponse> replenishBill(@RequestBody ReplenishDto replenishDto) {
        billService.replenishBill(replenishDto);
        logger.debug("Bill successfully replenished!");
        return new ResponseEntity<>(new SuccessResponse(replenishDto.getCardId(), HttpStatus.OK.toString()), HttpStatus.OK);
    }

    @RequestMapping(value = "/transferMoney", method = RequestMethod.POST)
    public ResponseEntity<SuccessResponse> transferMoney(@RequestBody TransferDto transferDto) {
        cardAccessRights(transferDto.getFromCardId());
        cardService.transferMoney(transferDto);
        logger.debug("Money successfully transfered!");
        return new ResponseEntity<>(new SuccessResponse(transferDto.getFromCardId(), HttpStatus.OK.toString()), HttpStatus.OK);
    }

    //Только клиент может создавать себе карточку.
    @RequestMapping(value = "/createCard", method = RequestMethod.POST)
    public ResponseEntity<SuccessResponse> createCard(@RequestBody CardDto cardDto) {
        long userId = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        if (!(cardDto.getUserId() == userId)) {
            throw new NoAccessException("Access denied!");
        }
        long cardId = cardService.save(cardDto);
        logger.debug("Card successfully created!");
        return new ResponseEntity<>(new SuccessResponse(cardId, HttpStatus.CREATED.toString()), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{cardId}/histories", method = RequestMethod.GET)
    public ResponseEntity<List<HistoryDto>> getHistoriesOfCard(@PathVariable long cardId) {
        cardAccessRights(cardId);
        List<HistoryDto> histories = historyService.findCardHistory(cardId);
        logger.debug("Histories successfully found!");
        return new ResponseEntity<>(histories, HttpStatus.OK);
    }

    private void cardAccessRights(long cardId) {
        long userId = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        CardDto card = cardService.get(cardId);
        if (!(card.getUserId() == userId)) {
            throw new NoAccessException("Access denied!");
        }
    }
}
