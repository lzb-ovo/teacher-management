package com.lzb.cloudlike.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;

/**
 * projectName: cloudlike
 *
 * @author: 罗智博
 * time: 2020/10/30 18:53
 * description:
 */
@SpringBootApplication
@EnableDiscoveryClient
@RibbonClients
public class LikeApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(LikeApiApplication.class,args);
    }
}
