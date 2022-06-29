package com.example.kill.controller;

import com.alibaba.druid.mock.MockArray;
import com.example.kill.pojo.GoodsVo;
import com.example.kill.pojo.OrderDetail;
import com.example.kill.pojo.OrderInfo;
import com.example.kill.pojo.User;
import com.example.kill.result.CodeMsg;
import com.example.kill.result.Result;
import com.example.kill.service.GoodsService;
import com.example.kill.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private GoodsService goodsService;

    @RequestMapping("/detail/{orderId}")
    public String info(Model model, User user, @PathVariable("orderId") long orderId) {
        if (user == null) {
            model.addAttribute("ErrorMsg",CodeMsg.SESSION_ERROR.getMsg());
            return "error";
        }
        OrderInfo orderInfo = orderService.getByOrderId(orderId);
        if(orderInfo == null) {
            model.addAttribute("ErrorMsg",CodeMsg.ORDER_NOT_EXIST.getMsg());
            return "error";
        }

        long goodsId = orderInfo.getGoodsId();
        GoodsVo goods = goodsService.getGoodsVoById(goodsId);

        model.addAttribute("orderInfo",orderInfo);
        model.addAttribute("goods",goods);
        return "order_detail";
    }
}
