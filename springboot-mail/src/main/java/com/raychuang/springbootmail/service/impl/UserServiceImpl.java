package com.raychuang.springbootmail.service.impl;


import com.raychuang.springbootmail.dao.UserDao;
import com.raychuang.springbootmail.dto.UserRegisterRequest;
import com.raychuang.springbootmail.model.User;
import com.raychuang.springbootmail.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;


    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        return userDao.createUser(userRegisterRequest);
    }

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }
}
