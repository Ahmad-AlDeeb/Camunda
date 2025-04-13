package com.deeb.hiringprocess;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@SuppressWarnings("checkstyle:HideUtilityClassConstructor")
public class HiringProcessApplication {
    public static void main(String[] args) {
        SpringApplication.run(HiringProcessApplication.class, args);
    }
}
