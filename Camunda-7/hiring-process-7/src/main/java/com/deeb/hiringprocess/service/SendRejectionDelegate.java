package com.deeb.hiringprocess.service;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component("sendRejection")
public class SendRejectionDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
    }
}
