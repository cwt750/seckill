package com.example.kill.service;

import com.example.kill.pojo.GoodsVo;
import com.example.kill.pojo.OrderInfo;
import com.example.kill.pojo.SeckillOrder;
import com.example.kill.pojo.User;
import org.springframework.transaction.annotation.Transactional;

public interface OrderService {

    public SeckillOrder getOrderByUserIdGoodsId(int userId, long goodsId);


    @Transactional
    public OrderInfo createOrder(User user, GoodsVo goods);


    public OrderInfo getByOrderId(long orderId);
    public long getId();
}
