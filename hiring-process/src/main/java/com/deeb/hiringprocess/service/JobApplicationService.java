package com.deeb.hiringprocess.service;

import com.deeb.hiringprocess.camunda.client.ZeebeClient;
import com.deeb.hiringprocess.camunda.job.Job;
import com.deeb.hiringprocess.entity.JobApplication;
import com.deeb.hiringprocess.util.RequestBodyBuilder;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.deeb.hiringprocess.constant.CamundaConstant.PROCESS_DEFINITION_ID;
import static com.deeb.hiringprocess.constant.CamundaConstant.ZEEBE_TOKEN;
import static java.lang.String.format;

@Service
public class JobApplicationService {
    private final ZeebeClient zeebeClient;

    public JobApplicationService(ZeebeClient zeebeClient) {
        this.zeebeClient = zeebeClient;
    }

    public void create(JobApplication jobApplication) {
        Map<String, Object> requestBody =
                RequestBodyBuilder.startProcessInstance(PROCESS_DEFINITION_ID, jobApplication);

        zeebeClient.startProcessInstance(ZEEBE_TOKEN, requestBody);
    }

    public void calculateCvScore(Job job) {
        Long jobKey = job.jobKey();
        Map<String, Object> completeRequestBody = RequestBodyBuilder.completeJob(Map.of("score", 95));

        System.out.println(format("Calculating %s's CV score... 🔃", job.variables().get("name")));
        zeebeClient.completeJob(ZEEBE_TOKEN, jobKey, completeRequestBody);
        System.out.println("CV score calculated. ✅");
    }

    public void scheduleInterview(Long userTaskKey) throws Exception {
        System.out.println("Scheduling interview... 🔃");
        zeebeClient.completeUserTask(ZEEBE_TOKEN, userTaskKey);
        System.out.println("Interview scheduled. ✅");
    }
}
