package com.deeb.hiringprocess.camunda.client;

import com.deeb.hiringprocess.camunda.process.ProcessInstance;
import com.deeb.hiringprocess.camunda.job.ActivatedJobs;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Map;

import static com.deeb.hiringprocess.constant.CamundaConstant.ZEEBE_REST_ADDRESS;

@Component
public class ZeebeClient {
    private final RestClient restClient;

    public ZeebeClient() {
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

    public void completeUserTask(String authToken, Long userTaskKey) throws Exception {
        restClient.post()
                .uri("/v2/user-tasks/{userTaskKey}/completion", userTaskKey)
                .header("Authorization", "Bearer " + authToken)
                .retrieve()
                .body(new ParameterizedTypeReference<>(){});
    }
}
