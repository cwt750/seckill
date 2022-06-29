package com.example.kill.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class MQConfig {
    public static final String SECKILL_QUEUE = "seckill.queue";

    @Bean
    public Queue seckillqueue(){
        return new Queue(SECKILL_QUEUE,true);
    }


}
