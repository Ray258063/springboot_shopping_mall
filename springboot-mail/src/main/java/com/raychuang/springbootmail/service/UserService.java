package com.raychuang.springbootmail.service;

import com.raychuang.springbootmail.dto.UserRegisterRequest;
import com.raychuang.springbootmail.model.User;

public interface UserService {
    Integer register(UserRegisterRequest userRegisterRequest);

    User getUserById(Integer userId);

}
