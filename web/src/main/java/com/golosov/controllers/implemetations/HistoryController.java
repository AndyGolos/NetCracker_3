package com.golosov.controllers.implemetations;

import com.golosov.controllers.abstracts.AbstractCrudController;
import com.golosov.services.dto.dto.HistoryDto;
import com.golosov.services.interfaces.BaseService;
import com.golosov.services.interfaces.HistoryService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Андрей on 23.05.2017.
 */
@RestController
@RequestMapping("/api/histories")
public class HistoryController extends AbstractCrudController<HistoryDto> {

    @Autowired
    private HistoryService historyService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<HistoryDto> saveHistory(@RequestBody HistoryDto historyDto) {
        return save(historyDto);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateHistory(@RequestBody HistoryDto historyDto, @PathVariable long id) {
        return update(historyDto, id);
    }

    @Override
    protected BaseService<HistoryDto> getService() {
        return historyService;
    }

    @Override
    protected HistoryDto getResponseDto() {
        return new HistoryDto();
    }
}
