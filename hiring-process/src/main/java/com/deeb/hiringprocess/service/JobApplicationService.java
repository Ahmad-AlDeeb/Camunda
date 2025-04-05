package com.deeb.hiringprocess.service;

import com.deeb.hiringprocess.entity.Job;
import com.deeb.hiringprocess.entity.JobApplication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.deeb.hiringprocess.Constant.CamundaConstant.PROCESS_DEFINITION_ID;
import static com.deeb.hiringprocess.Constant.CamundaConstant.ZEEBE_TOKEN;
import static java.lang.String.format;

@Service
public class JobApplicationService {
    private final ZeebeClientService zeebeClientService;

    public JobApplicationService(ZeebeClientService zeebeClientService) {
        this.zeebeClientService = zeebeClientService;
    }

    public void create(JobApplication jobApplication) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("processDefinitionId", PROCESS_DEFINITION_ID);
        requestBody.put("variables", jobApplication);

        zeebeClientService.startProcessInstance(ZEEBE_TOKEN, requestBody);
        calculateCvScore();
    }

    public void calculateCvScore() {
        Map<String, Object> activateRequestBody = new HashMap<>();
        activateRequestBody.put("type", "CalculateCvScore");
        activateRequestBody.put("maxJobsToActivate", 1);
        activateRequestBody.put("timeout", 10000);

        Job activatedJob = zeebeClientService.activateJobs(ZEEBE_TOKEN, activateRequestBody).jobs().getFirst();
        Long jobKey = activatedJob.jobKey();
        String name = (String) activatedJob.variables().get("name");

        System.out.println(format("Calculating %s's CV score...", name));

        Map<String, Object> completeRequestBody = new HashMap<>();
        completeRequestBody.put("variables", Map.of("score", 90));
        zeebeClientService.completeJob(ZEEBE_TOKEN, jobKey, completeRequestBody);
    }
}
