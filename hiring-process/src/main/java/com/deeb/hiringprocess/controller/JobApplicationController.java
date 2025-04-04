package com.deeb.hiringprocess.controller;

import com.deeb.hiringprocess.entity.ApiResponse;
import com.deeb.hiringprocess.entity.JobApplication;
import com.deeb.hiringprocess.service.JobApplicationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.deeb.hiringprocess.Constant.ApiResponseConstant.RESOURCE_CREATED;
import static java.lang.String.format;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/job-applications")
public class JobApplicationController {

    private final JobApplicationService jobApplicationService;
    private static final String RESOURCE_NAME = JobApplication.class.getSimpleName();

    public JobApplicationController(JobApplicationService jobApplicationService) {
        this.jobApplicationService = jobApplicationService;
    }

    @PostMapping
    public ApiResponse<JobApplication> create(@RequestBody JobApplication jobApplication) {
        jobApplicationService.create(jobApplication);
        return new ApiResponse<>(CREATED.value(), format(RESOURCE_CREATED, RESOURCE_NAME), jobApplication);
    }
}
