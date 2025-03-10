package org.paymentprocess;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;

import java.util.Map;

public class PaymentHandler implements JobHandler {
    PaymentService paymentService = new PaymentService();

    @Override
    public void handle(JobClient client, ActivatedJob job) {

        // Get the process variables as a Map from the job
        final Map<String, Object> processVariables = job.getVariablesAsMap();
        final Integer amount = (Integer) processVariables.get("amount");

        // Delegate the payment processing to the PaymentService
        paymentService.processPayment(amount);
        client.newCompleteCommand(job.getKey()).send().join();
    }

}
