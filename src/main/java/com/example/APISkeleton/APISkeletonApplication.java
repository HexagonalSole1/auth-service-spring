package com.example.APISkeleton;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class APISkeletonApplication {

    public static void main(String[] args) {
        SpringApplication.run(APISkeletonApplication.class, args);
    }

}
