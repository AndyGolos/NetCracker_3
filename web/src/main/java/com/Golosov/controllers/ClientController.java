package com.Golosov.controllers;

import com.Golosov.entities.Type;
import com.Golosov.services.dto.dto.CardDto;
import com.Golosov.services.dto.dto.HistoryDto;
import com.Golosov.services.dto.dto.TypeDto;
import com.Golosov.services.dto.dto.UserDto;
import com.Golosov.services.interfaces.CardService;
import com.Golosov.services.interfaces.HistoryService;
import com.Golosov.services.interfaces.TypeService;
import com.Golosov.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    private HistoryService historyService;
    @Autowired
    private TypeService typeService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public long sss() {
        TypeDto typeDto = new TypeDto();
        typeDto.setType("vip");
        return typeService.save(typeDto);
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public UserDto userInfo(@PathVariable long id) {
        return userService.get(id);
    }


    /*@RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @ResponseBody
    public User userSave(@PathVariable long )*/


    @RequestMapping(value = "/{id}/cards", method = RequestMethod.GET)
    @ResponseBody
    public Set<CardDto> userCards(@PathVariable long id) {
        return cardService.findUsersCards(id);
    }

    @RequestMapping(value = "/{id}/cards/{cardid}", method = RequestMethod.GET)
    @ResponseBody
    public CardDto userCard(@PathVariable long cardid) {
        return cardService.get(cardid);
    }


    @RequestMapping(value = "/card/{id}/histories", method = RequestMethod.GET)
    @ResponseBody
    public Set<HistoryDto> cardHistories(@PathVariable long id) {
        return historyService.findCardHistory(id);
    }
}
