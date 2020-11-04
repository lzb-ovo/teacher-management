package com.lzb.cloudlike.api.config;

import com.netflix.loadbalancer.BestAvailableRule;
import com.netflix.loadbalancer.IRule;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * projectName: cloudlike
 *
 * @author: 罗智博
 * time: 2020/10/30 18:55
 * description:
 */
@Configuration
public class RibbonConfig {
    @Bean
    @LoadBalanced//启动负载均衡
    public RestTemplate createRT(){
        return new RestTemplate();
    }
    @Bean//负载均衡的分发策略
    public IRule createRule(){
        //最小并发分配
        return new BestAvailableRule();
    }
}
