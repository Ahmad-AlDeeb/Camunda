package com.deeb.hiringprocess.camunda.job;

import com.deeb.hiringprocess.camunda.client.OperateClient;
import com.deeb.hiringprocess.camunda.client.RestZeebeClient;
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
public class RestJobWorkers {
    private final RestZeebeClient restZeebeClient;
    private final OperateClient operateClient;
    private final JobApplicationService jobApplicationService;

    public RestJobWorkers(RestZeebeClient restZeebeClient, OperateClient operateClient,
                          JobApplicationService jobApplicationService) {
        this.restZeebeClient = restZeebeClient;
        this.operateClient = operateClient;
        this.jobApplicationService = jobApplicationService;
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
                String isInterested = jobApplicationService.scheduleInterview();

                Long userTaskKey = flowNodes.getFirst().key() + 1;
                requestBody =  RequestBodyBuilder.completeJob(Map.of("isInterested", isInterested));
                restZeebeClient.completeUserTask(userTaskKey, requestBody);

                completedTaskKeys.add(userTaskKey);
            }
        }
    }

//    @Async
//    public void doInterview() throws Exception {
//        Map<String, Object> requestBody =
//                RequestBodyBuilder.searchFlowNodes("Activity_DoInterview", FlowNodeState.ACTIVE);
//
//        Set<Long> completedTaskKeys = new HashSet<>();
//        while (true) {
//            List<FlowNode> flowNodes = operateClient.searchFlowNodes(requestBody).items();
//
//            if (flowNodes.isEmpty() || completedTaskKeys.contains(flowNodes.getFirst().key() + 1)) {
//                Thread.sleep(3000);
//            } else {
//                Long userTaskKey = flowNodes.getFirst().key() + 1;
//                completedTaskKeys.add(userTaskKey);
//                jobApplicationService.doInterview(userTaskKey);
//            }
//        }
//    }

//    @Async
//    public void submitApplicantResponse() throws Exception {
//        Map<String, Object> requestBody =
//                RequestBodyBuilder.searchFlowNodes("Activity_SubmitApplicantResponse", FlowNodeState.ACTIVE);
//
//        Set<Long> completedTaskKeys = new HashSet<>();
//        while (true) {
//            List<FlowNode> flowNodes = operateClient.searchFlowNodes(requestBody).items();
//
//            if (flowNodes.isEmpty() || completedTaskKeys.contains(flowNodes.getFirst().key() + 1)) {
//                Thread.sleep(3000);
//            } else {
//                Long userTaskKey = flowNodes.getFirst().key() + 1;
//                completedTaskKeys.add(userTaskKey);
//                jobApplicationService.submitApplicantResponse(userTaskKey);
//            }
//        }
//    }
}
