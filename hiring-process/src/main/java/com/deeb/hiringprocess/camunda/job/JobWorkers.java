package com.deeb.hiringprocess.camunda.job;

import com.deeb.hiringprocess.camunda.flownode.FlowNode;
import com.deeb.hiringprocess.camunda.flownode.FlowNodeState;
import com.deeb.hiringprocess.camunda.client.OperateClient;
import com.deeb.hiringprocess.camunda.client.ZeebeClient;
import com.deeb.hiringprocess.service.JobApplicationService;
import com.deeb.hiringprocess.util.RequestBodyBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.deeb.hiringprocess.constant.CamundaConstant.OPERATE_TOKEN;
import static com.deeb.hiringprocess.constant.CamundaConstant.ZEEBE_TOKEN;

@Component
public class JobWorkers {
    private final ZeebeClient zeebeClient;
    private final OperateClient operateClient;
    private final JobApplicationService jobApplicationService;

    public JobWorkers(ZeebeClient zeebeClient, OperateClient operateClient, JobApplicationService jobApplicationService) {
        this.zeebeClient = zeebeClient;
        this.operateClient = operateClient;
        this.jobApplicationService = jobApplicationService;
    }

    @Async
    public void calculateCvScore() throws Exception {
        Map<String, Object> requestBody =
                RequestBodyBuilder.activateJobs("CalculateCvScore", 10000, 1);

        while (true) {
            List<Job> jobs = zeebeClient.activateJobs(ZEEBE_TOKEN, requestBody).jobs();

            if (jobs.isEmpty()) {
                Thread.sleep(1000);
            }
            else {
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
            List<FlowNode> flowNodes = operateClient.searchFlowNodes(OPERATE_TOKEN, requestBody).items();

            if (flowNodes.isEmpty() || completedTaskKeys.contains(flowNodes.getFirst().key() + 1)) {
                Thread.sleep(3000);
            }
            else {
                Long userTaskKey = flowNodes.getFirst().key() + 1;
                completedTaskKeys.add(userTaskKey);
                jobApplicationService.scheduleInterview(userTaskKey);
            }
        }
    }
}
