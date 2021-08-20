package com.schedule.keep_alive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class KeepAliveApplication {

    public static void main(String[] args) {
        SpringApplication.run(KeepAliveApplication.class, args);
    }

}
