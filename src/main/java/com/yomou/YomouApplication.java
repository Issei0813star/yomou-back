package com.yomou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class YomouApplication {

    public static void main(String[] args) {
        SpringApplication.run(YomouApplication.class, args);
    }

}
