package com.deeb.hiringprocess.entity;
import java.util.Map;

public record ProcessInstance(
        Long processDefinitionKey,
        Long processInstanceKey,
        String processDefinitionId,
        Integer processDefinitionVersion,
        String tenantId,
        Map<String, Object> variables
) {}