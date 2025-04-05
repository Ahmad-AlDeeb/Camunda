package com.deeb.hiringprocess;

import com.deeb.hiringprocess.service.JobApplicationService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.CompletableFuture;

@SpringBootApplication
public class HiringProcessApplication {
	public static void main(String[] args) {
		SpringApplication.run(HiringProcessApplication.class, args);
		CompletableFuture.runAsync(() -> {
			try {
				JobApplicationService.calculateCvScore();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
	}
}
