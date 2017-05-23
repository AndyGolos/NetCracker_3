package com.Golosov.controllers.implemetations;

import com.Golosov.controllers.abstracts.AbstractCrudController;
import com.Golosov.services.dto.dto.HistoryDto;
import com.Golosov.services.interfaces.BaseService;
import com.Golosov.services.interfaces.HistoryService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Андрей on 23.05.2017.
 */
@RestController
@RequestMapping("/histories")
public class HistoryController extends AbstractCrudController<HistoryDto> {

    private final Logger logger = Logger.getLogger(HistoryController.class);

    @Autowired
    private HistoryService historyService;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity saveHistory(@RequestBody HistoryDto historyDto) {
        return save(historyDto);
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public ResponseEntity updateHistory(@RequestBody HistoryDto historyDto) {
        return update(historyDto);
    }

    @Override
    protected BaseService<HistoryDto> getService() {
        return historyService;
    }
}
