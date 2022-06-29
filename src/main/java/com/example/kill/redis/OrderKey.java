package com.example.kill.redis;


public class OrderKey extends BasePrefix {
    public OrderKey(String prefix) {
        super(prefix);
    }


    public static OrderKey getOrderByUidOid = new OrderKey("ok");
}
