package com.deeb.hiringprocess.service;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component("saveApplication")
public class SaveApplicationDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
    }
}
