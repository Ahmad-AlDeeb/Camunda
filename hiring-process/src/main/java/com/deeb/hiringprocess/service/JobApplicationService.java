package com.deeb.hiringprocess.service;

import com.deeb.hiringprocess.entity.Job;
import com.deeb.hiringprocess.entity.JobApplication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static com.deeb.hiringprocess.Constant.CamundaConstant.PROCESS_DEFINITION_ID;
import static com.deeb.hiringprocess.Constant.CamundaConstant.ZEEBE_TOKEN;
import static java.lang.String.format;

@Service
public class JobApplicationService {
    private static final ZeebeClientService zeebeClientService = new ZeebeClientService();

    public void create(JobApplication jobApplication) {
        Map<String, Object> requestBody = Map.of(
                "processDefinitionId", PROCESS_DEFINITION_ID,
                "variables", jobApplication
        );
        zeebeClientService.startProcessInstance(ZEEBE_TOKEN, requestBody);
    }

    public static void calculateCvScore() throws Exception {
        Map<String, Object> activateRequestBody = Map.of(
                "type", "CalculateCvScore",
                "timeout", 10000,
                "maxJobsToActivate", 1
        );
        while (true) {
            List<Job> jobs = zeebeClientService.activateJobs(ZEEBE_TOKEN, activateRequestBody).jobs();

            if (jobs.isEmpty()) {
                Thread.sleep(1000);
            } else for (Job job : jobs) {
                Long jobKey = job.jobKey();
                String name = (String) job.variables().get("name");

                System.out.println(format("Calculating %s's CV score...", name));

                Map<String, Object> completeRequestBody = Map.of("variables", Map.of("score", 95));
                zeebeClientService.completeJob(ZEEBE_TOKEN, jobKey, completeRequestBody);
            }
        }
    }


}
