package com.deeb.hiringprocess.service;

import com.deeb.hiringprocess.sendgrid.EmailDispatcher;
import jakarta.inject.Named;
import lombok.AllArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import static com.deeb.hiringprocess.constant.HiringConstant.TO_EMAIL;

@Named
@AllArgsConstructor
public class SendRejectionDelegate implements JavaDelegate {
    private final EmailDispatcher emailDispatcher;
    private static final String SUBJECT = "Job Application Updates from Qeema Company";
    private static final String BODY = "Thank you for your interest in the position. Unfortunately, ...";

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        emailDispatcher.dispatchEmail(TO_EMAIL, SUBJECT, BODY);
    }
}
