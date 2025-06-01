package com.deeb.hiringprocess;

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableProcessApplication
public class HiringProcess {
  public static void main(String... args) {
    SpringApplication.run(HiringProcess.class, args);
  }
}