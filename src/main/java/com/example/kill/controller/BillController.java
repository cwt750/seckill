package com.example.kill.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.example.dubbobill.service.BillService;
import com.example.kill.pojo.User;
import com.example.kill.result.CodeMsg;
import com.example.kill.result.Result;
import com.example.kill.utils.MD5Util;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;

@Controller
@RequestMapping("/paybill")
public class BillController {
    @Reference
    BillService billService;
    @RequestMapping("/result")
    @ResponseBody
    public Result<Integer> paybill(User user, @RequestParam("orderPrice") BigDecimal orderPrice){
        long id=user.getId();
        int p=orderPrice.intValue();
        int res=billService.killgoods(id, p);
        if(res==0){
            return Result.error(CodeMsg.PAY_BILL_FAIL);
        }
        else{
            return Result.success(0);
        }
    }
    @RequestMapping("/success")
    public String success(){
        return "success";
    }

    @RequestMapping("/addsuccess")
    public String addMoneySyccess(){
        return "addsuccess";
    }
    @RequestMapping("/addmoney")
    public String addmoney(Model model,User user){
        model.addAttribute("user",user);
        return "addsomemoney";
    }

    @RequestMapping("/putmoney")
    @ResponseBody
    public Result<Integer> add(User user, @RequestParam("money") String money_t, @RequestParam("password") String password){
        int money=Integer.valueOf(money_t);
        System.out.println(money_t);
        String pwd1=user.getPassword();
        String saltDB = user.getSalt();
        String pwd2 = MD5Util.formPassToDBPass(password, saltDB);
        if(pwd1.equals(pwd2)){
            if(billService.addmoney((long) user.getId(),money)>0){
                return Result.success(0);
            }
            else{
                System.out.println("222");
                return Result.error(CodeMsg.ADD_MONEY_FAIL);
            }
        }
        else{
            System.out.println("333");
            return Result.error(CodeMsg.ADD_MONEY_PASSWORD_ERROR);
        }
    }
}
