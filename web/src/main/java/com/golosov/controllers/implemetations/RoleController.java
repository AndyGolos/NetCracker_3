package com.golosov.controllers.implemetations;

import com.golosov.controllers.abstracts.AbstractCrudController;
import com.golosov.controllers.responses.SuccessResponse;
import com.golosov.services.dto.dto.RoleDto;
import com.golosov.services.interfaces.BaseService;
import com.golosov.services.interfaces.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Андрей on 23.05.2017.
 */
@RestController
@RequestMapping("/api/roles")
public class RoleController extends AbstractCrudController<RoleDto> {

    private RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<SuccessResponse> saveRole(@RequestBody RoleDto roleDto) {
        return save(roleDto);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateRole(@RequestBody RoleDto roleDto, @PathVariable long id) {
        return update(roleDto, id);
    }

    @Override
    protected BaseService<RoleDto> getService() {
        return roleService;
    }
}
