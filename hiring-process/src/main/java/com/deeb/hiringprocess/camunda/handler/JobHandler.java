package com.deeb.hiringprocess.camunda.handler;

import com.deeb.hiringprocess.service.JobApplicationService;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.annotation.Variable;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class JobHandler {
    private final JobApplicationService jobApplicationService;

    public JobHandler(JobApplicationService jobApplicationService) {
        this.jobApplicationService = jobApplicationService;
    }

    @JobWorker(type = "CalculateCvScore")
    public Map<String, Object> calculateCvScoreHandler(JobClient client, ActivatedJob job, @Variable String name) {
        return Map.of("score", jobApplicationService.calculateCvScore(name));
    }

    @JobWorker(type = "SaveApplication")
    public void saveApplicationHandler(JobClient client, ActivatedJob job, @Variable String name) {
        jobApplicationService.saveApplication(name);
    }

    @JobWorker(type = "UpdateApplication")
    public void updateApplicationHandler(JobClient client, ActivatedJob job, @Variable String name) {
        jobApplicationService.updateApplication(name);
    }
}
