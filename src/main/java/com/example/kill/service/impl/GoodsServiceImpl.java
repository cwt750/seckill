package com.example.kill.service.impl;

import com.example.kill.dao.GoodsMapper;
import com.example.kill.pojo.GoodsVo;
import com.example.kill.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    GoodsMapper goodsMapper;


    public List<GoodsVo> listGoodVo() {
        return goodsMapper.listGoodsVo();
    }


    public GoodsVo getGoodsVoById(long goodsId) {
        return goodsMapper.getGoodsVoById(goodsId);
    }


    public int reduceStock(GoodsVo goods) {
        long goodsId = goods.getId();
        return goodsMapper.reduceStock(goodsId);
    }
}
