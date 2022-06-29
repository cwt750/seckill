package com.example.kill.controller;

import com.example.kill.pojo.User;
import com.example.kill.rabbitmq.MQSender;
import com.example.kill.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/test")
public class SampleController {
    @RequestMapping("/userInfo")
    @ResponseBody
    public User thymeleaf(Model model, User user) {
        return user;
    }


}
