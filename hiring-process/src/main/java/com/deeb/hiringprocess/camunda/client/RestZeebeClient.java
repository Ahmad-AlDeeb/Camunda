package com.deeb.hiringprocess.camunda.client;

import com.deeb.hiringprocess.camunda.job.ActivatedJobs;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Map;


@Component
public class RestZeebeClient {
    @Value("${ZEEBE_REST_ADDRESS}")
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

    public void startProcessInstance(Map<String, Object> requestBody) {
        restClient.post()
                .uri("/v2/process-instances")
                .body(requestBody)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }

    public ActivatedJobs activateJobs(Map<String, Object> requestBody) {
        return restClient.post()
                .uri("/v2/jobs/activation")
                .body(requestBody)
                .retrieve()
                .body(ActivatedJobs.class);
    }

    public void completeJob(Long jobKey, Map<String, Object> requestBody) {
        restClient.post()
                .uri("/v2/jobs/{jobKey}/completion", jobKey)
                .body(requestBody)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }

    public void completeUserTask(Long userTaskKey, Map<String, Object> requestBody) throws Exception {
        restClient.post()
                .uri("/v2/user-tasks/{userTaskKey}/completion", userTaskKey)
                .body(requestBody)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }
}
