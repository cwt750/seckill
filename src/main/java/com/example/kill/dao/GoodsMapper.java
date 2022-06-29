package com.example.kill.dao;

import com.example.kill.pojo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface GoodsMapper {
    public List<GoodsVo> listGoodsVo();
    public GoodsVo getGoodsVoById(@Param("goodsId") long goodsId);
    public int reduceStock(@Param("goodsId")long goodsId);
}
