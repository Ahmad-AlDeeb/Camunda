package com.deeb.hiringprocess.camunda.client;

import com.deeb.hiringprocess.camunda.flownode.FoundFlowNodes;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Map;

import static com.deeb.hiringprocess.constant.CamundaConstant.OPERATE_BASE_URL;
import static com.deeb.hiringprocess.constant.CamundaConstant.OPERATE_TOKEN;

@Component
public class OperateClient {
    private final RestClient restClient;

    public OperateClient() {
        restClient = RestClient.builder()
                .baseUrl(OPERATE_BASE_URL)
                .defaultHeader("Authorization", "Bearer " + OPERATE_TOKEN)
                .build();
    }

    public FoundFlowNodes searchFlowNodes(Map<String, Object> requestBody) {
        return restClient.post()
                .uri("/v1/flownode-instances/search")
                .body(requestBody)
                .retrieve()
                .body(FoundFlowNodes.class);
    }
}
