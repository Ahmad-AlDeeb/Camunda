package com.deeb.hiringprocess.camunda;

import java.util.Map;

public record ProcessInstance(
        Long processDefinitionKey,
        Long processInstanceKey,
        String processDefinitionId,
        Integer processDefinitionVersion,
        String tenantId,
        Map<String, Object> variables
) {
}
