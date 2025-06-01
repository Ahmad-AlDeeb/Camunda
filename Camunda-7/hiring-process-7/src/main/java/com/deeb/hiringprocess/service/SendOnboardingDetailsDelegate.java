package com.deeb.hiringprocess.service;

import com.deeb.hiringprocess.sendgrid.EmailDispatcher;
import jakarta.inject.Named;
import lombok.AllArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import static com.deeb.hiringprocess.constant.HiringConstant.TO_EMAIL;

@Named
@AllArgsConstructor
public class SendOnboardingDetailsDelegate implements JavaDelegate {
    private final EmailDispatcher emailDispatcher;
    private static final String SUBJECT = "Qeema Onboarding Details";
    private static final String BODY = "This is the onboarding details for the new employee. Review carefully.";

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        emailDispatcher.dispatchEmail(TO_EMAIL, SUBJECT, BODY);
    }
}
