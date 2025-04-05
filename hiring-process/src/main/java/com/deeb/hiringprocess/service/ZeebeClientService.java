package com.deeb.hiringprocess.service;

import com.deeb.hiringprocess.entity.ActivatedJobs;
import com.deeb.hiringprocess.entity.ProcessInstance;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Map;

import static com.deeb.hiringprocess.Constant.CamundaConstant.ZEEBE_REST_ADDRESS;

@Component
public class ZeebeClientService {
    private final RestClient restClient;

    public ZeebeClientService() {
        restClient = RestClient.builder()
                .baseUrl(ZEEBE_REST_ADDRESS)
                .build();
    }

    public ProcessInstance startProcessInstance(String authToken, Map<String, Object> requestBody) {
        return restClient.post()
                .uri("/v2/process-instances")
                .header("Authorization", "Bearer " + authToken)
                .body(requestBody)
                .retrieve()
                .body(ProcessInstance.class);
    }

    public ActivatedJobs activateJobs(String authToken, Map<String, Object> requestBody) {
        return restClient.post()
                .uri("/v2/jobs/activation")
                .header("Authorization", "Bearer " + authToken)
                .body(requestBody)
                .retrieve()
                .body(ActivatedJobs.class);
    }

    public void completeJob(String authToken, Long jobKey, Map<String, Object> requestBody) {
        restClient.post()
                .uri("/v2/jobs/{jobKey}/completion", jobKey)
                .header("Authorization", "Bearer " + authToken)
                .body(requestBody)
                .retrieve()
                .body(new ParameterizedTypeReference<>(){});
    }
}
