package com.raychuang.springbootmail.controller;

import com.raychuang.springbootmail.dto.UserLoginRequest;
import com.raychuang.springbootmail.dto.UserRegisterRequest;
import com.raychuang.springbootmail.model.User;
import com.raychuang.springbootmail.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserController {

    @Autowired
    private UserService userService;
    //註冊
    @PostMapping("/users/register")
    public ResponseEntity<User> register(@RequestBody @Valid UserRegisterRequest userRegisterRequest){
        //創建一個帳號及密碼 創建完成回傳 Id回來
        Integer userId=userService.register(userRegisterRequest);
        //使用id 去查有沒有創建完成這個使用者並回傳給前端
        User user=userService.getUserById(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
    //登入
    @PostMapping("/users/login")
    public ResponseEntity<User> login(@RequestBody @Valid UserLoginRequest userLoginRequest){
        User user=userService.login(userLoginRequest);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }


}
