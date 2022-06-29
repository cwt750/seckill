package com.example.kill.rabbitmq;

import com.example.kill.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SeckillMessage {
    private User user;
    private long goodsId;

}
