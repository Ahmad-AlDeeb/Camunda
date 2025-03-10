package org.paymentprocess;

import io.camunda.zeebe.client.api.worker.JobWorker;
import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProvider;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProviderBuilder;

import java.time.Duration;

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

            final JobWorker paymentWorker = client.newWorker()
                    .jobType("processPayment")
                    .handler(new PaymentHandler())
                    .timeout(Duration.ofSeconds(10).toMillis())
                    .open();
            Thread.sleep(100000);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
