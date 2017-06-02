package com.golosov.controllers.abstracts;

import com.golosov.services.dto.dto.BaseDto;
import com.golosov.services.interfaces.BaseService;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by Андрей on 23.05.2017.
 */
public abstract class AbstractCrudController<T extends BaseDto> {

    private final Logger logger = Logger.getLogger(AbstractCrudController.class);

    protected abstract BaseService<T> getService();

    protected abstract T getResponseDto();

    protected ResponseEntity<T> save(T dto) {
        long id = getService().save(dto);
        logger.debug("Successfully saved!");
        T responseEntity = createResponseEntity(id, HttpStatus.CREATED.toString());
        return new ResponseEntity<>(responseEntity, HttpStatus.CREATED);
    }

    protected ResponseEntity<T> update(T dto) {
        getService().update(dto);
        logger.debug("Successfully updated!");
        T responseEntity = createResponseEntity(dto.getId(), HttpStatus.OK.toString());
        return new ResponseEntity<>(responseEntity, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    protected ResponseEntity<T> delete(@PathVariable long id) {
        getService().delete(id);
        logger.debug("Successfully deleted!");
        T responseEntity = createResponseEntity(id, HttpStatus.OK.toString());
        return new ResponseEntity<>(responseEntity, HttpStatus.OK);
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
    
    private T createResponseEntity(long id, String status) {
        T responseDto = getResponseDto();
        responseDto.setId(id);
        responseDto.setHttpStatus(status);
        return responseDto;
    }
}
