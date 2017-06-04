package com.golosov.services.interfaces;

import com.golosov.services.dto.dto.RoleDto;

import java.util.Set;

/**
 * Created by Андрей on 17.05.2017.
 */
public interface RoleService extends BaseService<RoleDto>{

    Set<RoleDto> userRoles(long id);
}
