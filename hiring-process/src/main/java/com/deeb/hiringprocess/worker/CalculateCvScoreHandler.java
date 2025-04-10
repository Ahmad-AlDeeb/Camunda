package com.deeb.hiringprocess.worker;

import com.deeb.hiringprocess.service.JobApplicationService;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;

public class CalculateCvScoreHandler implements JobHandler {
    JobApplicationService jobApplicationService = new JobApplicationService();

    @Override
    public void handle(JobClient client, ActivatedJob job) {
        String name = (String) job.getVariable("name");

        Integer score = jobApplicationService.calculateCvScore(name);

        client.newCompleteCommand(job.getKey())
                .variable("score", score)
                .send()
                .join();
    }
}
