package com.deeb.hiringprocess.camunda.handler;

import com.deeb.hiringprocess.service.JobApplicationService;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.annotation.Variable;
import org.springframework.stereotype.Component;

@Component
public class JobHandler {
    private final JobApplicationService jobApplicationService;

    public JobHandler(JobApplicationService jobApplicationService) {
        this.jobApplicationService = jobApplicationService;
    }

    @JobWorker(type = "CalculateCvScore")
    public void calculateCvScoreHandler(JobClient client, ActivatedJob job, @Variable String name) {
        Integer score = jobApplicationService.calculateCvScore(name);
        client.newCompleteCommand(job.getKey())
                .variable("score", score)
                .send()
                .join();
    }

    @JobWorker(type = "SaveApplication")
    public void saveApplicationHandler(JobClient client, ActivatedJob job, @Variable String name) {
        jobApplicationService.saveApplication(name);
        client.newCompleteCommand(job.getKey())
                .send()
                .join();
    }

    @JobWorker(type = "UpdateApplication")
    public void updateApplicationHandler(JobClient client, ActivatedJob job, @Variable String name) {
        jobApplicationService.updateApplication(name);
        client.newCompleteCommand(job.getKey())
                .send()
                .join();
    }
}
