package com.deeb.hiringprocess.camunda.util;

import java.util.Map;

public final class RequestBodyBuilder {
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
