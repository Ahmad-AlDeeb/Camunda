package com.deeb.hiringprocess.camunda.client;

import com.deeb.hiringprocess.camunda.task.Task;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Component
public class TasklistClient {

    @Value("${TASKLIST_BASE_URL}")
    private String TASKLIST_BASE_URL;

    @Value("${TASKLIST_TOKEN}")
    private String TASKLIST_TOKEN;

    private RestClient restClient;

    @PostConstruct
    public void init() {
        restClient = RestClient.builder()
                .baseUrl(TASKLIST_BASE_URL)
                .defaultHeader("Authorization", "Bearer " + TASKLIST_TOKEN)
                .build();
    }

    public List<Task> searchTasks(Map<String, Object> requestBody) {
        return restClient.post()
                .uri("/v1/tasks/search")
                .body(requestBody)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }

    public void completeTask(Long taskKey, Map<String, Object> requestBody) {
        restClient.post()
                .uri("/v1/tasks/" + taskKey + "/complete")
                .body(requestBody)
                .retrieve()
                .body(String.class);
    }
}
