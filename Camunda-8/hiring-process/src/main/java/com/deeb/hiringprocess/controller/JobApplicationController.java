package com.deeb.hiringprocess.controller;

import com.deeb.hiringprocess.camunda.WorkflowManager;
import com.deeb.hiringprocess.entity.JobApplication;
import com.deeb.hiringprocess.util.ApiResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.deeb.hiringprocess.util.ApiResponseCreator.createUnifiedResponse;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/job-applications")
public class JobApplicationController {
    private final WorkflowManager workflowManager;

    public JobApplicationController(WorkflowManager workflowManager) {
        this.workflowManager = workflowManager;
    }

    @PostMapping
    public ApiResponse<JobApplication> create(@RequestBody JobApplication jobApplication) {
        workflowManager.startProcessInstance(jobApplication);
        return createUnifiedResponse(CREATED.value(), "Job application was created!", jobApplication);
    }
}
