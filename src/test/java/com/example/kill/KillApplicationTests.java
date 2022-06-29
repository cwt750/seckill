package com.example.kill;

import com.alibaba.dubbo.config.annotation.Reference;
import com.example.dubbobill.service.BillService;
import com.example.kill.pojo.GoodsVo;
import com.example.kill.service.GoodsService;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest

class KillApplicationTests {
    @Autowired
    GoodsService goodsService;
    @Test
    void contextLoads() {
        List<GoodsVo> goodsVos = goodsService.listGoodVo();
        for(GoodsVo goodsVo:goodsVos){
            System.out.println(goodsVo);
        }
    }

}
