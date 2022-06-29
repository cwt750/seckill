package com.example.kill.service;

import com.example.kill.pojo.LoginParam;
import com.example.kill.pojo.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserService {

    public static final String COOKI_NAME_TOKEN = "token";


    String login(HttpServletRequest request,HttpServletResponse response, LoginParam loginParam);


    public User getByToken(HttpServletResponse response, String token);
}
