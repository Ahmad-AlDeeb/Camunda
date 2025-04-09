package com.deeb.hiringprocess.camunda.job;

import com.deeb.hiringprocess.camunda.client.OperateClient;
import com.deeb.hiringprocess.camunda.client.ZeebeClient;
import com.deeb.hiringprocess.camunda.flownode.FlowNode;
import com.deeb.hiringprocess.camunda.flownode.FlowNodeState;
import com.deeb.hiringprocess.service.JobApplicationService;
import com.deeb.hiringprocess.util.RequestBodyBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class JobWorkers {
    private final ZeebeClient zeebeClient;
    private final OperateClient operateClient;
    private final JobApplicationService jobApplicationService;


    public JobWorkers(
            ZeebeClient zeebeClient, OperateClient operateClient, JobApplicationService jobApplicationService) {
        this.zeebeClient = zeebeClient;
        this.operateClient = operateClient;
        this.jobApplicationService = jobApplicationService;
    }

    @Async
    public void calculateCvScore() throws Exception {
        Map<String, Object> requestBody =
                RequestBodyBuilder.activateJobs("CalculateCvScore", 10000, 1);

        while (true) {
            List<Job> jobs = zeebeClient.activateJobs(requestBody).jobs();

            if (jobs.isEmpty()) {
                Thread.sleep(1000);
            } else {
                jobApplicationService.calculateCvScore(jobs.getFirst());
            }
        }
    }

    @Async
    public void scheduleInterview() throws Exception {
        Map<String, Object> requestBody =
                RequestBodyBuilder.searchFlowNodes("Activity_ScheduleInterview", FlowNodeState.ACTIVE);

        Set<Long> completedTaskKeys = new HashSet<>();
        while (true) {
            List<FlowNode> flowNodes = operateClient.searchFlowNodes(requestBody).items();

            if (flowNodes.isEmpty() || completedTaskKeys.contains(flowNodes.getFirst().key() + 1)) {
                Thread.sleep(3000);
            } else {
                Long userTaskKey = flowNodes.getFirst().key() + 1;
                completedTaskKeys.add(userTaskKey);
                jobApplicationService.scheduleInterview(userTaskKey);
            }
        }
    }

    @Async
    public void saveApplication() throws Exception {
        Map<String, Object> requestBody =
                RequestBodyBuilder.activateJobs("SaveApplication", 10000, 1);

        while (true) {
            List<Job> jobs = zeebeClient.activateJobs(requestBody).jobs();

            if (jobs.isEmpty()) {
                Thread.sleep(1000);
            } else {
                jobApplicationService.saveApplication(jobs.getFirst());
            }
        }
    }

    @Async
    public void doInterview() throws Exception {
        Map<String, Object> requestBody =
                RequestBodyBuilder.searchFlowNodes("Activity_DoInterview", FlowNodeState.ACTIVE);

        Set<Long> completedTaskKeys = new HashSet<>();
        while (true) {
            List<FlowNode> flowNodes = operateClient.searchFlowNodes(requestBody).items();

            if (flowNodes.isEmpty() || completedTaskKeys.contains(flowNodes.getFirst().key() + 1)) {
                Thread.sleep(3000);
            } else {
                Long userTaskKey = flowNodes.getFirst().key() + 1;
                completedTaskKeys.add(userTaskKey);
                jobApplicationService.doInterview(userTaskKey);
            }
        }
    }

    @Async
    public void submitApplicantResponse() throws Exception {
        Map<String, Object> requestBody =
                RequestBodyBuilder.searchFlowNodes("Activity_SubmitApplicantResponse", FlowNodeState.ACTIVE);

        Set<Long> completedTaskKeys = new HashSet<>();
        while (true) {
            List<FlowNode> flowNodes = operateClient.searchFlowNodes(requestBody).items();

            if (flowNodes.isEmpty() || completedTaskKeys.contains(flowNodes.getFirst().key() + 1)) {
                Thread.sleep(3000);
            } else {
                Long userTaskKey = flowNodes.getFirst().key() + 1;
                completedTaskKeys.add(userTaskKey);
                jobApplicationService.submitApplicantResponse(userTaskKey);
            }
        }
    }

    @Async
    public void sendOnboardingDetails() throws Exception {
        Map<String, Object> requestBody =
                RequestBodyBuilder.activateJobs("sendOnboardingDetails", 10000, 1);

        while (true) {
            List<Job> jobs = zeebeClient.activateJobs(requestBody).jobs();

            if (jobs.isEmpty()) {
                Thread.sleep(1000);
            } else {
                jobApplicationService.sendOnboardingDetails(jobs.getFirst());
            }
        }
    }

    @Async
    public void updateApplication() throws Exception {
        Map<String, Object> requestBody =
                RequestBodyBuilder.activateJobs("UpdateApplication", 10000, 1);

        while (true) {
            List<Job> jobs = zeebeClient.activateJobs(requestBody).jobs();

            if (jobs.isEmpty()) {
                Thread.sleep(1000);
            } else {
                jobApplicationService.updateApplication(jobs.getFirst());
            }
        }
    }
}
