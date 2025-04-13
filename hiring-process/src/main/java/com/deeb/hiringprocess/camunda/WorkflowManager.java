package com.deeb.hiringprocess.camunda;

import com.deeb.hiringprocess.entity.JobApplication;
import io.camunda.zeebe.client.ZeebeClient;
import org.springframework.stereotype.Component;

@Component
public class WorkflowManager {

    private final ZeebeClient client;

    public WorkflowManager(ZeebeClient client, UserTaskWorkers userTaskWorkers) {
        this.client = client;
        userTaskWorkers.scheduleInterview();
        userTaskWorkers.doInterview();
        userTaskWorkers.submitApplicantResponse();
    }

    public void startProcessInstance(JobApplication jobApplication) {
        client.newCreateInstanceCommand()
                .bpmnProcessId("Process_Hiring")
                .latestVersion()
                .variables(jobApplication)
                .send()
                .join();
    }
}
