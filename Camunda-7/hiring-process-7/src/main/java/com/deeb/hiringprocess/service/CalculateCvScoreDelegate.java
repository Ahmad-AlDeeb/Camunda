package com.deeb.hiringprocess.service;

import io.micrometer.tracing.annotation.NewSpan;
import jakarta.inject.Named;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

@Named
public class CalculateCvScoreDelegate implements JavaDelegate {
    @Override
    @NewSpan("calculateCvScore")
    public void execute(DelegateExecution execution) throws Exception {
        execution.setVariable("score", 90);
    }
}
