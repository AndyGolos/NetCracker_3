package com.golosov.controllers.abstracts;

import com.golosov.controllers.responses.SuccessResponse;
import com.golosov.services.dto.dto.BaseDto;
import com.golosov.services.interfaces.BaseService;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by Андрей on 23.05.2017.
 */
public abstract class AbstractCrudController<T extends BaseDto> extends BaseController {

    private final Logger logger = Logger.getLogger(AbstractCrudController.class);

    protected abstract BaseService<T> getService();

    protected ResponseEntity<SuccessResponse> save(T dto) {
        long id = getService().save(dto);
        logger.debug("Successfully saved!");
        return new ResponseEntity<>(new SuccessResponse(id, HttpStatus.CREATED.toString()), HttpStatus.CREATED);
    }

    protected ResponseEntity<SuccessResponse> update(T dto, long id) {
        getService().update(dto, id);
        logger.debug("Successfully updated!");
        return new ResponseEntity<>(new SuccessResponse(id, HttpStatus.OK.toString()), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    protected ResponseEntity<SuccessResponse> delete(@PathVariable long id) {
        getService().delete(id);
        logger.debug("Successfully deleted!");
        return new ResponseEntity<>(new SuccessResponse(id, HttpStatus.OK.toString()), HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    protected ResponseEntity<List<T>> getAll() {
        List<T> list = getService().getAll();
        logger.debug("All dtos successfully found!");
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    protected ResponseEntity<T> get(@PathVariable long id) {
        T object = getService().get(id);
        logger.debug("Dto successfully found!");
        return new ResponseEntity<>(object, HttpStatus.OK);
    }
}
