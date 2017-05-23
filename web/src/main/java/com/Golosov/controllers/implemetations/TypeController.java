package com.Golosov.controllers.implemetations;

import com.Golosov.controllers.abstracts.AbstractCrudController;
import com.Golosov.services.dto.dto.TypeDto;
import com.Golosov.services.interfaces.BaseService;
import com.Golosov.services.interfaces.TypeService;
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
@RequestMapping("/types")
public class TypeController extends AbstractCrudController<TypeDto> {

    private final Logger logger = Logger.getLogger(TypeController.class);

    @Autowired
    private TypeService typeService;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity saveType(@RequestBody TypeDto typeDto) {
        return save(typeDto);
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public ResponseEntity updateType(@RequestBody TypeDto typeDto) {
        return update(typeDto);
    }

    @Override
    protected BaseService<TypeDto> getService() {
        return typeService;
    }
}
