package com.Golosov.controllers.abstracts;

import com.Golosov.services.dto.dto.BaseDto;
import com.Golosov.services.interfaces.BaseService;
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
public abstract class AbstractCrudController<T extends BaseDto>{

    private final Logger logger = Logger.getLogger(AbstractCrudController.class);

    protected abstract BaseService<T> getService();

    protected ResponseEntity<Long> save(T dto) {

        long id = getService().save(dto);
        return new ResponseEntity<Long>(id, HttpStatus.OK);
    }

    protected ResponseEntity update(T dto) {
        getService().update(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    protected ResponseEntity delete(@PathVariable long id) {
        getService().delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    protected ResponseEntity<List<T>> getAll() {
        List<T> list = getService().getAll();
        return new ResponseEntity<List<T>>(list, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    protected ResponseEntity<T> get(@PathVariable long id) {
        T object = getService().get(id);
        return new ResponseEntity<T>(object, HttpStatus.OK);
    }
}
