package com.deeb.hiringprocess.camunda.util;

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

    public static Map<String, Object> searchTasks(String taskDefinitionId) {
        return Map.of("taskDefinitionId", taskDefinitionId);
    }

    private RequestBodyBuilder() {
        throw new IllegalStateException("Utility class");
    }
}
