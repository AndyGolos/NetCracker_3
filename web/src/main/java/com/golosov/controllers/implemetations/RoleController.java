package com.golosov.controllers.implemetations;

import com.golosov.controllers.abstracts.AbstractCrudController;
import com.golosov.services.dto.dto.RoleDto;
import com.golosov.services.interfaces.BaseService;
import com.golosov.services.interfaces.RoleService;
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
@RequestMapping("/roles")
public class RoleController extends AbstractCrudController<RoleDto> {

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<RoleDto> saveRole(@RequestBody RoleDto roleDto) {
        return save(roleDto);
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public ResponseEntity updateRole(@RequestBody RoleDto roleDto) {
        return update(roleDto);
    }

    @Override
    protected BaseService<RoleDto> getService() {
        return roleService;
    }

    @Override
    protected RoleDto getResponseDto() {
        return new RoleDto();
    }
}
