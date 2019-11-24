package com.hqw.pro.hspboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.hqw.pro.hspboot"})
public class HspbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(HspbootApplication.class, args);
    }

}
