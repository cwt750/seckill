package com.example.kill.service.impl;

import com.example.kill.pojo.GoodsVo;
import com.example.kill.pojo.OrderInfo;
import com.example.kill.pojo.SeckillOrder;
import com.example.kill.pojo.User;
import com.example.kill.redis.RedisService;
import com.example.kill.redis.SeckillKey;
import com.example.kill.service.GoodsService;
import com.example.kill.service.OrderService;
import com.example.kill.service.SeckillService;
import com.example.kill.utils.MD5Util;
import com.example.kill.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class SeckillServiceImpl implements SeckillService {
    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private RedisService redisService;


    @Transactional
    public OrderInfo seckill(User user, GoodsVo goods) {
        //减库存
        int flag = goodsService.reduceStock(goods);
        if(flag == 1)
            //创建订单:order_info  seckill_order
            return orderService.createOrder(user,goods);
        else {
            //已无库存
            setGoodsOver(goods.getId());
            return null;
        }
    }


    public long getSeckillResult(int id, long goodsId) {
        SeckillOrder order = orderService.getOrderByUserIdGoodsId(id, goodsId);
        if(order != null) {
            return order.getOrderId();
        } else {
            boolean isOver = getGoodsOver(goodsId);
            if(isOver) {
                return -1;
            }else {
                return 0;
            }
        }
    }


    private void setGoodsOver(Long goodsId) {
        redisService.set(SeckillKey.isGoodsOver, ""+goodsId, true , 3600);
    }

    private boolean getGoodsOver(long goodsId) {
        return redisService.exists(SeckillKey.isGoodsOver, ""+goodsId);
    }



    public String createPath(User user, long goodsId) {
        String str = MD5Util.md5(UUIDUtil.uuid() + "987655");
        redisService.set(SeckillKey.getSeckillPath,""+user.getId()+"_"+goodsId,str,60);
        return str;
    }


    public boolean checkPath(User user, long goodsId, String path) {
        if(user == null || path == null) {
            return false;
        }
        String pathOld = redisService.get(SeckillKey.getSeckillPath, ""+user.getId() + "_"+ goodsId, String.class);
        return path.equals(pathOld);
    }


}
