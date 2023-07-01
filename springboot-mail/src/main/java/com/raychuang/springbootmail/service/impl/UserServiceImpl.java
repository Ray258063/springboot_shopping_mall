package com.raychuang.springbootmail.service.impl;


import com.raychuang.springbootmail.dao.UserDao;
import com.raychuang.springbootmail.dto.UserLoginRequest;
import com.raychuang.springbootmail.dto.UserRegisterRequest;
import com.raychuang.springbootmail.model.User;
import com.raychuang.springbootmail.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class UserServiceImpl implements UserService {

    //創建一個log變數
    private final static Logger log= LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;


    @Override
    //regiser 裡面還有其他的邏輯
    public Integer register(UserRegisterRequest userRegisterRequest) {
        User user=userDao.getUserByEmail(userRegisterRequest.getEmail());

        //檢查註冊的email
        //不等於null代表已經註冊過了
        if(user!=null){
            log.warn("該email {} 已經被註冊了",userRegisterRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        //創建帳號
        //Dao 裡的create 的意思是在資料庫裡創建一筆數據
        return userDao.createUser(userRegisterRequest);
    }

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public User login(UserLoginRequest userLoginRequest) {
        //根據前端傳入的email值到資料庫裡尋找那一筆user數據
        User user=userDao.getUserByEmail(userLoginRequest.getEmail());
        if(user==null){
            log.warn("該 email {} 尚未註冊", userLoginRequest.getEmail());
            //強制停止前端的請求
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if(user.getPassword().equals(userLoginRequest.getPassword())){
            return user;
        }

        else{
            log.warn("email {} 的密碼不正確",userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

    }
}
