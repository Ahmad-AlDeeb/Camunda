package com.deeb.hiringprocess.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class JobApplicationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(JobApplicationService.class);

    public Integer calculateCvScore(String name) {
        LOGGER.info("Calculating {}'s CV score... 🔃", name);
        return 95;
    }

    public String scheduleInterview() {
        LOGGER.info("Scheduling interview... 🔃");
        return "yes";
    }

    public void saveApplication(String name) {
        LOGGER.info("Saving {}'s Job Application... 🔃", name);
    }

    public String doInterview() {
        LOGGER.info("Doing interview... 🔃");
        return "yes";
    }

    public String submitApplicantResponse() {
        LOGGER.info("Submitting applicant's response... 🔃");
        return "yes";
    }

    public void updateApplication(String name) {
        LOGGER.info("Updating {}'s Job Application... 🔃", name);
    }
}
