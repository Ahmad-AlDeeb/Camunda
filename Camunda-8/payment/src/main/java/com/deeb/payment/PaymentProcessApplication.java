package com.deeb.payment;

import io.camunda.zeebe.client.ZeebeClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PaymentProcessApplication implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(PaymentProcessApplication.class);

    @Autowired
    private ZeebeClient zeebeClient;

    public static void main(String[] args) {
        SpringApplication.run(PaymentProcessApplication.class, args);
    }

    @Override
    public void run(final String... args) {// or whatever the key is
        zeebeClient.newCreateInstanceCommand()
                .bpmnProcessId("process-payments")
                .latestVersion()
                .send()
                .join();
    }
}
