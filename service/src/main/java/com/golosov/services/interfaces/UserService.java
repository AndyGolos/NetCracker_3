package com.golosov.services.interfaces;

import com.golosov.services.dto.dto.UserDto;

/**
 * Created by Андрей on 17.05.2017.
 */
public interface UserService extends BaseService<UserDto> {

    UserDto findUserInfo(UserDto userDto);

    long saveAdmin(UserDto userDto);

    long login(UserDto userDto);
}
