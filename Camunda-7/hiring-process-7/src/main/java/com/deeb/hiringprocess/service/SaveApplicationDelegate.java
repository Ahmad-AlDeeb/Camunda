package com.deeb.hiringprocess.service;

import io.micrometer.tracing.annotation.NewSpan;
import jakarta.inject.Named;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

@Named
public class SaveApplicationDelegate implements JavaDelegate {
    @Override
    @NewSpan("saveApplication")
    public void execute(DelegateExecution execution) throws Exception {
    }
}
