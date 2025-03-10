package org.paymentprocess;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;

public class PaymentHandler implements JobHandler {
    PaymentService paymentService = new PaymentService();

    @Override
    public void handle(JobClient client, ActivatedJob job) {
        paymentService.processPayment();
        client.newCompleteCommand(job.getKey()).send().join();
    }

}
