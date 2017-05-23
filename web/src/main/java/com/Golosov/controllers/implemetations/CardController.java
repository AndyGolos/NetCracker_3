package com.Golosov.controllers.implemetations;

import com.Golosov.controllers.abstracts.AbstractCrudController;
import com.Golosov.services.dto.dto.CardDto;
import com.Golosov.services.interfaces.BaseService;
import com.Golosov.services.interfaces.CardService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Андрей on 18.05.2017.
 */
@RestController
public class CardController extends AbstractCrudController<CardDto> {

    private final Logger logger = Logger.getLogger(CardController.class);

    @Autowired
    private CardService cardService;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity saveCard(@RequestBody CardDto cardDto) {
        return save(cardDto);
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public ResponseEntity updateCard(@RequestBody CardDto cardDto) {
        return update(cardDto);
    }

    @Override
    protected BaseService<CardDto> getService() {
        return cardService;
    }
}
