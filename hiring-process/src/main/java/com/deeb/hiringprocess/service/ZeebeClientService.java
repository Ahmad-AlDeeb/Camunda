package com.deeb.hiringprocess.service;

import com.deeb.hiringprocess.entity.ApiResponse;
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

    public ApiResponse<ProcessInstance> startProcessInstance(String authToken, Map<String, Object> requestBody) {
        return restClient.post()
                .uri("/v2/process-instances")
                .header("Authorization", "Bearer " + authToken)
                .body(requestBody)
                .retrieve()
                .body(new ParameterizedTypeReference<>(){});
    }
}
