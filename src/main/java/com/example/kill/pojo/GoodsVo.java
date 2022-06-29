package com.example.kill.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsVo extends Goods{
    private Integer stockCount;

    private Date startDate;

    private Date endDate;

    private BigDecimal seckillPrice;
}
