package com.example.kill.redis;


public class SeckillKey extends BasePrefix {
    private SeckillKey(String prefix) {
        super(prefix);
    }
    public static SeckillKey isGoodsOver = new SeckillKey("go");
    public static SeckillKey getSeckillPath = new SeckillKey("sp");
}
