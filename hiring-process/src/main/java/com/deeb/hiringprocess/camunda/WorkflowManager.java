package com.deeb.hiringprocess.camunda;

import com.deeb.hiringprocess.camunda.handler.UserTaskHandler;
import com.deeb.hiringprocess.entity.JobApplication;
import io.camunda.zeebe.client.ZeebeClient;
import org.springframework.stereotype.Component;

@Component
public class WorkflowManager {

    private final ZeebeClient client;

    public WorkflowManager(ZeebeClient client, UserTaskHandler userTaskHandler) {
        this.client = client;
        userTaskHandler.scheduleInterview();
        userTaskHandler.doInterview();
        userTaskHandler.submitApplicantResponse();
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
