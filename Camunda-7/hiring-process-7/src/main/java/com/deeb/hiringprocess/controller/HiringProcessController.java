package com.deeb.hiringprocess.controller;

import lombok.AllArgsConstructor;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/hiring-process")
public class HiringProcessController {
    private RuntimeService runtimeService;

    @PostMapping("/application")
    public void submitJobApplication(@RequestBody Map<String, Object> applicationData) {
        runtimeService.startProcessInstanceByKey("hiring-process-7", applicationData);
    }

    @PostMapping("/offer")
    public void submitOfferResponse(@RequestBody Map<String, Object> responseData) {
        Map<String, Object> correlationKeys = new HashMap<>();
        Map<String, Object> processVariables = (Map<String, Object>) responseData.get("processVariables");
        runtimeService.correlateMessage(responseData.get("messageName").toString(), correlationKeys, processVariables);
    }
}
