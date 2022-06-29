package com.example.kill.rabbitmq;

import com.example.kill.redis.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class MQSender {
    private static Logger log = LoggerFactory.getLogger(MQSender.class);

    @Resource
    AmqpTemplate amqpTemplate ;

    public void sendSeckillMessage(SeckillMessage sm) {
        String msg = RedisService.beanToString(sm);
        log.info("send message:"+msg);
        amqpTemplate.convertAndSend(MQConfig.SECKILL_QUEUE, msg);
    }
}

