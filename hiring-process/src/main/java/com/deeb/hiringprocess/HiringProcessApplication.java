package com.deeb.hiringprocess;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import static com.deeb.hiringprocess.util.ZeebeUtils.registerJobWorkers;

@SpringBootApplication
@EnableAsync
public class HiringProcessApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(HiringProcessApplication.class, args);
        registerJobWorkers();
    }
}
