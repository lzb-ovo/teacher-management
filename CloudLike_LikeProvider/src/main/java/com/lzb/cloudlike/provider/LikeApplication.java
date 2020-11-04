package com.lzb.cloudlike.provider;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * projectName: cloudlike
 *
 * @author: 罗智博
 * time: 2020/10/30 16:21
 * description:
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages = "com.lzb.cloudlike.provider.dao")
@EnableScheduling//启用定时任务
public class LikeApplication {
    public static void main(String[] args) {
        SpringApplication.run(LikeApplication.class,args);
    }
}
