package com.example.kill.controller;

import com.example.kill.access.AccessLimit;
import com.example.kill.pojo.GoodsVo;
import com.example.kill.pojo.OrderInfo;
import com.example.kill.pojo.SeckillOrder;
import com.example.kill.pojo.User;
import com.example.kill.rabbitmq.MQSender;
import com.example.kill.rabbitmq.SeckillMessage;
import com.example.kill.redis.GoodsKey;
import com.example.kill.redis.RedisService;
import com.example.kill.result.CodeMsg;
import com.example.kill.result.Result;
import com.example.kill.service.GoodsService;
import com.example.kill.service.OrderService;
import com.example.kill.service.SeckillService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/seckill")
public class SeckillController implements InitializingBean {
    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private SeckillService seckillService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private MQSender sender;

    @RequestMapping(value="/{path}/seckill_mq",method = RequestMethod.POST)
    @ResponseBody
    public Result<Integer> seckillMq(User user, @RequestParam("goodsId")long goodsId,
                                     @PathVariable("path") String path) {
        if(user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }


        boolean checkPath = seckillService.checkPath(user, goodsId, path);
        if(!checkPath) {
            return Result.error(CodeMsg.REQUEST_ILLEGAL);
        }

        long stock = redisService.decr(GoodsKey.getSeckillGoodsStock, "" + goodsId);//10
        if (stock < 0) {
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }

        SeckillOrder order = orderService.getOrderByUserIdGoodsId(user.getId(), goodsId);
        if(order != null) {
            return Result.error(CodeMsg.REPEATE_MIAOSHA);
        }


        SeckillMessage sm = new SeckillMessage();
        sm.setUser(user);
        sm.setGoodsId(goodsId);
        sender.sendSeckillMessage(sm);
        return Result.success(0);//排队中
    }

    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> goodsList = goodsService.listGoodVo();
        if (goodsList == null) {
            return;
        }
        for (GoodsVo goods : goodsList) {
            redisService.set(GoodsKey.getSeckillGoodsStock, "" + goods.getId(), goods.getStockCount(),1800);
        }
    }

    @RequestMapping(value = "/result", method = RequestMethod.GET)
    @ResponseBody
    public Result<Long> seckillResult(@RequestParam("goodsId") long goodsId,User user) {
        if (user == null) {
            return Result.error(CodeMsg.USER_NO_LOGIN);
        }
        long result = seckillService.getSeckillResult(user.getId(), goodsId);
        return Result.success(result);
    }

    @AccessLimit(seconds=5, maxCount=5, needLogin=true)
    @RequestMapping(value = "/path", method = RequestMethod.GET)
    @ResponseBody
    public Result<String> getSeckillPath(User user, @RequestParam("goodsId") long goodsId) {
        if (user == null) {
            return Result.error(CodeMsg.USER_NO_LOGIN);
        }
        String path = seckillService.createPath(user, goodsId);
        return Result.success(path);
    }
}
