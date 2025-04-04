package com.deeb.hiringprocess.service;

import com.deeb.hiringprocess.entity.JobApplication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.deeb.hiringprocess.Constant.CamundaConstant.PROCESS_DEFINITION_ID;
import static com.deeb.hiringprocess.Constant.CamundaConstant.ZEEBE_TOKEN;

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
    }
}
