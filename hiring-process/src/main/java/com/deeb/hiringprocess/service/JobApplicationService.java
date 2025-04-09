package com.deeb.hiringprocess.service;

import com.deeb.hiringprocess.camunda.client.ZeebeClient;
import com.deeb.hiringprocess.camunda.job.Job;
import com.deeb.hiringprocess.entity.JobApplication;
import com.deeb.hiringprocess.util.RequestBodyBuilder;
import com.deeb.hiringprocess.util.WhatsappClient;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;

@Service
public class JobApplicationService {
    private final ZeebeClient zeebeClient;
    private final WhatsappClient whatsappClient;

    public JobApplicationService(ZeebeClient zeebeClient, WhatsappClient whatsappClient) {
        this.zeebeClient = zeebeClient;
        this.whatsappClient = whatsappClient;
    }

    public void create(JobApplication jobApplication) {
        Map<String, Object> requestBody =

                RequestBodyBuilder.startProcessInstance("Process_Hiring", jobApplication);

        zeebeClient.startProcessInstance(requestBody);
    }

    public void calculateCvScore(Job job) {
        Long jobKey = job.jobKey();
        Map<String, Object> requestBody = RequestBodyBuilder.completeJob(Map.of("score", 95));

        System.out.println(format("Calculating %s's CV score... 🔃", job.variables().get("name")));
        zeebeClient.completeJob(jobKey, requestBody);
        System.out.println("CV score calculated. ✅");
    }

    public void scheduleInterview(Long userTaskKey) throws Exception {
        Map<String, Object> requestBody = RequestBodyBuilder.completeJob(Map.of("isInterested", "yes"));

        System.out.println("Scheduling interview... 🔃");
        zeebeClient.completeUserTask(userTaskKey, requestBody);
        System.out.println("Interview scheduled. ✅");
    }

    public void saveApplication(Job job) {
        Long jobKey = job.jobKey();

        System.out.println(format("Saving %s's Job Application... 🔃", job.variables().get("name")));
        zeebeClient.completeJob(jobKey, new HashMap<>());
        System.out.println("Job Application Saved. ✅");
    }

    public void doInterview(Long userTaskKey) throws Exception {
        Map<String, Object> requestBody = RequestBodyBuilder.completeJob(Map.of("isFit", "yes"));

        System.out.println("Doing interview... 🔃");
        zeebeClient.completeUserTask(userTaskKey, requestBody);
        System.out.println("Interview done. ✅");
    }

    public void submitApplicantResponse(Long userTaskKey) throws Exception {
        Map<String, Object> requestBody = RequestBodyBuilder.completeJob(Map.of("isOfferAccepted", "yes"));

        System.out.println("Submitting applicant's response... 🔃");
        zeebeClient.completeUserTask(userTaskKey, requestBody);
        System.out.println("Applicant's response submitted. ✅");
    }

    public void sendOnboardingDetails(Job job) throws Exception {
        Long jobKey = job.jobKey();

        System.out.println(format("Sending onboarding details to %s... 🔃", job.variables().get("name")));
        whatsappClient.sendMessage();
        zeebeClient.completeJob(jobKey, new HashMap<>());
        System.out.println("Onboarding details sent. ✅");
    }

    public void updateApplication(Job job) {
        Long jobKey = job.jobKey();
        String name = (String) job.variables().get("name");
        String status = (String) job.variables().get("status");

        System.out.println(format("Updating %s's application status... 🔃", name));
        zeebeClient.completeJob(jobKey, new HashMap<>());
        System.out.println(format("%s was %s!!!", name, status));
    }
}
