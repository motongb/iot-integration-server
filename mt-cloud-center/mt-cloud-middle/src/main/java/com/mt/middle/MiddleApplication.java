package com.mt.middle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class MiddleApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiddleApplication.class, args);
    }

}
