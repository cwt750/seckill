package com.example.kill.service;

import com.example.kill.pojo.GoodsVo;


import java.util.List;

public interface GoodsService {


    public List<GoodsVo> listGoodVo();


    public GoodsVo getGoodsVoById(long goodsId);


    public int reduceStock(GoodsVo goods);
}
