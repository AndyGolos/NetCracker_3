package com.Golosov.services.interfaces;

import com.Golosov.entities.User;
import com.Golosov.services.dto.dto.UserDto;

/**
 * Created by Андрей on 17.05.2017.
 */
public interface UserService extends BaseService<UserDto>{
    UserDto findUserInfo(UserDto userDto);
}
