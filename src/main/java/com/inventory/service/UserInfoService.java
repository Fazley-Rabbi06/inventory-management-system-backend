package com.inventory.service;

import com.inventory.dto.UserDto;
import com.inventory.entity.User;

public interface UserInfoService {
    User createUser(UserDto userDto);
}
