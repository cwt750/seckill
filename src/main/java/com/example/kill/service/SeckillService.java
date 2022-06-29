package com.example.kill.service;


import com.example.kill.pojo.GoodsVo;
import com.example.kill.pojo.OrderInfo;
import com.example.kill.pojo.User;
import org.springframework.transaction.annotation.Transactional;

public interface SeckillService {

    @Transactional
    public OrderInfo seckill(User user, GoodsVo goodsVo);


    public long getSeckillResult(int id, long goodsId);


    public String createPath(User user, long goodsId);


    public boolean checkPath(User user, long goodsId,String path);
}
