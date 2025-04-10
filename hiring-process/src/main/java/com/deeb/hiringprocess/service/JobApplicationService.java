package com.deeb.hiringprocess.service;

import com.deeb.hiringprocess.util.RequestBodyBuilder;
import org.springframework.stereotype.Service;

import java.util.Map;

import static java.lang.String.format;

@Service
public class JobApplicationService {
    public Integer calculateCvScore(String name) {
        System.out.println(format("Calculating %s's CV score... 🔃", name));
        return 95;
    }

    public String scheduleInterview() throws Exception {
        System.out.println("Scheduling interview... 🔃");
        return "yes";
    }

    public void saveApplication(String name) {
        System.out.println(format("Saving %s's Job Application... 🔃", name));
    }

    public String doInterview() throws Exception {
        System.out.println("Doing interview... 🔃");
        return "yes";
    }

    public String submitApplicantResponse() throws Exception {
        System.out.println("Submitting applicant's response... 🔃");
        return "yes";
    }

    public String updateApplication(String name) {
        System.out.println(format("Updating %s's Job Application... 🔃", name));
        return "yes";
    }
}
