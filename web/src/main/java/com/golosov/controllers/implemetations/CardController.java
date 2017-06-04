package com.golosov.controllers.implemetations;

import com.golosov.controllers.abstracts.AbstractCrudController;
import com.golosov.controllers.responses.SuccessResponse;
import com.golosov.services.dto.dto.CardDto;
import com.golosov.services.interfaces.BaseService;
import com.golosov.services.interfaces.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Андрей on 18.05.2017.
 */
@RestController
@RequestMapping("/api/cards")
public class CardController extends AbstractCrudController<CardDto> {

    private CardService cardService;

    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<SuccessResponse> saveCard(@RequestBody CardDto cardDto) {
        return save(cardDto);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateCard(@RequestBody CardDto cardDto, @PathVariable long id) {
        return update(cardDto, id);
    }

    @Override
    protected BaseService<CardDto> getService() {
        return cardService;
    }
}
