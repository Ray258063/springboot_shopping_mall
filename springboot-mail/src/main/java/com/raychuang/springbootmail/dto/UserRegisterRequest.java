package com.raychuang.springbootmail.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UserRegisterRequest {

    @NotBlank //不能為Null 也不能為空的字串
    @Email //前端傳過來必須是email的格式
    private String email;
    @NotNull //不能為Null 也不能為空的字串
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
