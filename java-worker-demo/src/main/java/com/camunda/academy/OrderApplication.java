package com.camunda.academy;

import java.util.Map;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.camunda.academy.handler.OrderHandler;
import com.camunda.academy.handler.PackItemsHandler;
import com.camunda.academy.handler.ProcessPaymentHandler;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.worker.JobWorker;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProvider;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProviderBuilder;

import static com.camunda.academy.Constant.NUM_INSTANCES;
import static com.camunda.academy.Constant.PROCESS_ID;
import static com.camunda.academy.Constant.ZEEBE_ADDRESS;
import static com.camunda.academy.Constant.ZEEBE_AUTHORIZATION_SERVER_URL;
import static com.camunda.academy.Constant.ZEEBE_CLIENT_ID;
import static com.camunda.academy.Constant.ZEEBE_CLIENT_SECRET;
import static com.camunda.academy.Constant.ZEEBE_TOKEN_AUDIENCE;

public class OrderApplication {
    private static final Logger logger = LoggerFactory.getLogger(OrderApplication.class);

    public static void main(String[] args) {
        final OAuthCredentialsProvider credentialsProvider = new OAuthCredentialsProviderBuilder()
            .authorizationServerUrl(ZEEBE_AUTHORIZATION_SERVER_URL)
            .audience(ZEEBE_TOKEN_AUDIENCE)
            .clientId(ZEEBE_CLIENT_ID)
            .clientSecret(ZEEBE_CLIENT_SECRET)
            .build();

        try (final ZeebeClient client = ZeebeClient.newClientBuilder()
                .credentialsProvider(credentialsProvider)
                .gatewayAddress(ZEEBE_ADDRESS)
                .build()) {

            // Process Instance creator looper
            startProcessInstances(client, NUM_INSTANCES);

            final JobWorker OrderWorker = client.newWorker()
                .jobType("trackOrderStatus")
                .handler(new OrderHandler())
                .open();
            
            final JobWorker ProcessPaymentWorker = client.newWorker()
                .jobType("processPayment")
                .handler(new ProcessPaymentHandler())
                .open();
        
            final JobWorker PackItemsWorker = client.newWorker()
                .jobType("packItems")
                .handler(new PackItemsHandler())
                .open();
             
            // Terminate the worker with an Integer input
            Scanner sc = new Scanner(System.in);
            sc.nextInt();
            sc.close();
            OrderWorker.close();
            ProcessPaymentWorker.close();
            PackItemsWorker.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void startProcessInstances(ZeebeClient zeebeClient, int numInstances) {
        logger.info("Starting: " + numInstances + " process instances for process: " + PROCESS_ID);
        for (int i = 0; i < numInstances; i++) {
            FakeRandomizer fakeRandomizer = new FakeRandomizer();
            Map<String, Object> fakeRequest = fakeRandomizer.getRandom();
            logger.info("Generating Order({})",fakeRequest.get("orderId"));
            var event = zeebeClient.newCreateInstanceCommand()
                .bpmnProcessId(PROCESS_ID)
                .latestVersion()
                .variables(fakeRequest)
                .send()
                .join();
            logger.info("Process instance: {} started", event.getProcessInstanceKey());
        }
        logger.info("Ending: " + numInstances + " instances created for process: " + PROCESS_ID);
    }
}
