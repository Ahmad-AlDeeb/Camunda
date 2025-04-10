package com.deeb.hiringprocess;

import com.deeb.hiringprocess.camunda.job.JobWorkers;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class HiringProcessApplication implements CommandLineRunner {
    private final JobWorkers jobWorkers;

    public HiringProcessApplication(JobWorkers jobWorkers) {
        this.jobWorkers = jobWorkers;
    }

    public static void main(String[] args) {
        SpringApplication.run(HiringProcessApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        jobWorkers.calculateCvScore();
//        jobWorkers.scheduleInterview();
        jobWorkers.saveApplication();
        jobWorkers.doInterview();
        jobWorkers.submitApplicantResponse();
        jobWorkers.sendOnboardingDetails();
        jobWorkers.updateApplication();
    }
}
