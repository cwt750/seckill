package com.example.kill.dao;

import com.example.kill.pojo.OrderInfo;
import com.example.kill.pojo.SeckillOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectKey;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface OrderMapper {
    public SeckillOrder getOrderByUserIdGoodsId(@Param("userId")long userId, @Param("goodsId")long goodsId);
    @SelectKey(keyColumn = "id",keyProperty = "id",resultType = long.class, before = false,statement = "select last_insert_id()")
    public long insert(OrderInfo orderInfo);
    public int insertSeckillOrder(SeckillOrder seckillOrder);
    public OrderInfo getOrderById(@Param("orderId") long orderId);
    public long getId();
}
