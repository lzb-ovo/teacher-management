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
 * time: 2020/11/4 16:10
 * description:
 */
@SpringBootApplication
@MapperScan(basePackages = "com.lzb.cloudlike.provider.dao")
@EnableDiscoveryClient
@EnableScheduling
public class CarApplication {
    public static void main(String[] args) {
        SpringApplication.run(CarApplication.class,args);
    }
}
