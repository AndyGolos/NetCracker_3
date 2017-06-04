package com.golosov.controllers.implemetations;

import com.golosov.controllers.abstracts.AbstractCrudController;
import com.golosov.controllers.responses.SuccessResponse;
import com.golosov.services.dto.dto.TypeDto;
import com.golosov.services.interfaces.BaseService;
import com.golosov.services.interfaces.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Андрей on 23.05.2017.
 */
@RestController
@RequestMapping("/api/types")
public class TypeController extends AbstractCrudController<TypeDto> {

    private TypeService typeService;

    @Autowired
    public TypeController(TypeService typeService) {
        this.typeService = typeService;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<SuccessResponse> saveType(@RequestBody TypeDto typeDto) {
        return save(typeDto);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateType(@RequestBody TypeDto typeDto, @PathVariable long id) {
        return update(typeDto, id);
    }

    @Override
    protected BaseService<TypeDto> getService() {
        return typeService;
    }
}
