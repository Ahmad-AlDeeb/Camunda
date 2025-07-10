package com.deeb.hiringprocess.service;

import io.micrometer.tracing.annotation.NewSpan;
import jakarta.inject.Named;
import lombok.AllArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import com.deeb.hiringprocess.sendgrid.EmailDispatcher;

import static com.deeb.hiringprocess.constant.HiringConstant.TO_EMAIL;

@Named
@AllArgsConstructor
public class SendOfferDelegate implements JavaDelegate {
    private final EmailDispatcher emailDispatcher;
    private static final String SUBJECT = "Job Offer from Qeema Company";
    private static final String BODY =
            "Congratulations! We are pleased to offer you the position. Please review the attached offer letter.";

    @Override
    @NewSpan("sendOffer")
    public void execute(DelegateExecution execution) throws Exception {
        emailDispatcher.dispatchEmail(TO_EMAIL, SUBJECT, BODY);
    }
}
