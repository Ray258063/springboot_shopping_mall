package com.raychuang.springbootmail.service;

import com.raychuang.springbootmail.dto.UserLoginRequest;
import com.raychuang.springbootmail.dto.UserRegisterRequest;
import com.raychuang.springbootmail.model.User;

public interface UserService {

    //註冊
    Integer register(UserRegisterRequest userRegisterRequest);
    //用id取得數據
    User getUserById(Integer userId);
    //登入
    User login (UserLoginRequest userLoginRequest);


}
