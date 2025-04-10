package com.deeb.hiringprocess.camunda.worker;

import com.deeb.hiringprocess.service.JobApplicationService;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;

public class UpdateApplicationWorker implements JobHandler {
    JobApplicationService jobApplicationService = new JobApplicationService();

    @Override
    public void handle(JobClient client, ActivatedJob job) {
        String name = (String) job.getVariable("name");

        jobApplicationService.updateApplication(name);

        client.newCompleteCommand(job.getKey())
                .send()
                .join();
    }
}
