package com.deeb.hiringprocess.camunda.client;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Map;


@Component
public class RestZeebeClient {
    @Value("${CAMUNDA_CLIENT_ZEEBE_REST_ADDRESS}")
    private String zeebeRestAddress;

    @Value("${ZEEBE_TOKEN}")
    private String zeebeToken;

    private RestClient restClient;

    @PostConstruct
    public void init() {
        restClient = RestClient.builder()
                .baseUrl(zeebeRestAddress)
                .defaultHeader("Authorization", "Bearer " + zeebeToken)
                .build();
    }

    public void completeUserTask(Long userTaskKey, Map<String, Object> requestBody) {
        restClient.post()
                .uri("/v2/user-tasks/{userTaskKey}/completion", userTaskKey)
                .body(requestBody)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }
}
