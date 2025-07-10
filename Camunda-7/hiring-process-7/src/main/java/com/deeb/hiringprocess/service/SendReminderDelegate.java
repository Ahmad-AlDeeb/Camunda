package com.deeb.hiringprocess.service;

import com.deeb.hiringprocess.sendgrid.EmailDispatcher;
import io.micrometer.tracing.annotation.NewSpan;
import jakarta.inject.Named;
import lombok.AllArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import static com.deeb.hiringprocess.constant.HiringConstant.TO_EMAIL;

@Named
@AllArgsConstructor
public class SendReminderDelegate implements JavaDelegate {
    private final EmailDispatcher emailDispatcher;
    private static final String SUBJECT = "Reminder to respond to your job application";
    // write this email body in a way that it is very short
    private static final String BODY = "Dear Applicant, this is a gentle reminder to respond to your job application.";

    @Override
    @NewSpan("sendReminder")
    public void execute(DelegateExecution execution) throws Exception {
        emailDispatcher.dispatchEmail(TO_EMAIL, SUBJECT, BODY);
    }
}
