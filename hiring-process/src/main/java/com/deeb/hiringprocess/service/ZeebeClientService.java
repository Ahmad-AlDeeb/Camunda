package com.deeb.hiringprocess.service;

import com.deeb.hiringprocess.entity.ApiResponse;
import com.deeb.hiringprocess.entity.ProcessInstance;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import static com.deeb.hiringprocess.Constant.CamundaConstant.ZEEBE_REST_ADDRESS;

@Component
public class ZeebeClientService {
    private final RestClient restClient;

    public ZeebeClientService() {
        restClient = RestClient.builder()
                .baseUrl(ZEEBE_REST_ADDRESS)
                .build();
    }

    public ApiResponse<ProcessInstance> startProcessInstance(String authToken, String processDefinitionId) {
        return restClient.post()
                .uri("/process-instances")
                .header("Authorization", authToken)
                .body(processDefinitionId)
                .retrieve()
                .body(new ParameterizedTypeReference<>(){});
    }
}
