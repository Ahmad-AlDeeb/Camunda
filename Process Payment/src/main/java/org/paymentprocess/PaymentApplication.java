package org.paymentprocess;

import io.camunda.zeebe.client.api.worker.JobWorker;
import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProvider;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProviderBuilder;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static org.paymentprocess.Constant.ZEEBE_ADDRESS;
import static org.paymentprocess.Constant.ZEEBE_AUTHORIZATION_SERVER_URL;
import static org.paymentprocess.Constant.ZEEBE_CLIENT_ID;
import static org.paymentprocess.Constant.ZEEBE_CLIENT_SECRET;
import static org.paymentprocess.Constant.ZEEBE_TOKEN_AUDIENCE;


public class PaymentApplication {
    public static void main( String[] args )  {
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

            // Define variables to pass to process through the job worker
            final Map<String, Object> variables = new HashMap<>();
            variables.put("amount", (double) Double.valueOf(100.00));

            // Creates and starts an instance of the process
            client.newCreateInstanceCommand()
                    .bpmnProcessId("paymentProcess")
                    .latestVersion()
                    .variables(variables)
                    .send()
                    .join();

            // Register a job worker for the "processPayment" job type
            final JobWorker paymentWorker = client.newWorker()
                    .jobType("processPayment")
                    .handler(new PaymentHandler())
                    .timeout(Duration.ofSeconds(10).toMillis())
                    .open();
            Thread.sleep(10000);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
