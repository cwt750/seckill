package com.example.kill.controller;

import com.example.kill.pojo.LoginParam;
import com.example.kill.result.Result;
import com.example.kill.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


@Controller
@RequestMapping("/user")
public class LoginController {
    @Autowired
    UserService userService;

    @RequestMapping("/to_login")
    public String toLogin() {
        return "login";
    }

    @RequestMapping("/do_login")
    @ResponseBody
    public Result<String> doLogin(@Valid LoginParam loginParam, HttpServletResponse response, HttpServletRequest request) {
        System.out.println(loginParam);
        String token = userService.login(request,response,loginParam);
        return Result.success(token);
    }
}
