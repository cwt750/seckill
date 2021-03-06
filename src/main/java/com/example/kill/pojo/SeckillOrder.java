package com.example.kill.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeckillOrder {
    private Long id;

    private Long userId;

    private Long orderId;

    private Long goodsId;
}
