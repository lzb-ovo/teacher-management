package com.lzb.cloudlike.provider.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;


/**
 * projectName: cloudlike
 *
 * @author: 罗智博
 * time: 2020/11/3 13:12
 * description:
 */

public class RabbitMQConfig {
    //要用mq首先是要写config创建队列
    public static String qname_like="lik-sync";
    @Bean
    public Queue createQu(){
        return new Queue(qname_like);
    }
}
