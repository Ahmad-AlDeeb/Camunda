package com.deeb.hiringprocess.service;

import io.micrometer.tracing.annotation.NewSpan;
import jakarta.inject.Named;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

@Named
public class UpdateApplicationDelegate implements JavaDelegate {
    @Override
    @NewSpan("updateApplication")
    public void execute(DelegateExecution execution) throws Exception {
    }
}
