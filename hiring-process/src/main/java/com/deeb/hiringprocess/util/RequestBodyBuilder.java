package com.deeb.hiringprocess.util;

import com.deeb.hiringprocess.camunda.flownode.FlowNodeState;

import java.util.Map;

public final class RequestBodyBuilder {
    public static Map<String, Object> startProcessInstance(String processDefinitionID, Object variables) {
        return Map.of(
                "processDefinitionId", processDefinitionID,
                "variables", variables
        );
    }

    public static Map<String, Object> activateJobs(String type, int timeout, int maxJobsToActivate) {
        return Map.of(
                "type", type,
                "timeout", timeout,
                "maxJobsToActivate", maxJobsToActivate
        );
    }

    public static Map<String, Object> completeJob(Map<String, Object> variables) {
        return Map.of("variables", variables);
    }

    public static Map<String, Object> searchFlowNodes(String flowNodeId, FlowNodeState state) {
        return Map.of(
                "filter", Map.of(
                        "flowNodeId", flowNodeId,
                        "state", state
                )
        );
    }

    private RequestBodyBuilder() {
        throw new IllegalStateException("Utility class");
    }
}
