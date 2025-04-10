package com.deeb.hiringprocess.util;

import com.deeb.hiringprocess.camunda.job.RestJobWorkers;
import com.deeb.hiringprocess.entity.JobApplication;
import com.deeb.hiringprocess.worker.CalculateCvScoreHandler;
import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProvider;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProviderBuilder;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class ZeebeUtils {
    @Value("${ZEEBE_AUTHORIZATION_SERVER_URL}")
    private String ZEEBE_AUTHORIZATION_SERVER_URL;

    @Value("${ZEEBE_TOKEN_AUDIENCE}")
    private String ZEEBE_TOKEN_AUDIENCE;

    @Value("${ZEEBE_CLIENT_ID}")
    private String ZEEBE_CLIENT_ID;

    @Value("${ZEEBE_CLIENT_SECRET}")
    private String ZEEBE_CLIENT_SECRET;

    @Value("${ZEEBE_ADDRESS}")
    private String ZEEBE_ADDRESS;

    private OAuthCredentialsProvider credentialsProvider;
    private static ZeebeClient client;
    private static RestJobWorkers restJobWorkers;

    public ZeebeUtils(RestJobWorkers restJobWorkers) {
        this.restJobWorkers = restJobWorkers;
    }

    @PostConstruct
    void init() {
        credentialsProvider = new OAuthCredentialsProviderBuilder()
                .authorizationServerUrl(ZEEBE_AUTHORIZATION_SERVER_URL)
                .audience(ZEEBE_TOKEN_AUDIENCE)
                .clientId(ZEEBE_CLIENT_ID)
                .clientSecret(ZEEBE_CLIENT_SECRET)
                .build();
        client = ZeebeClient.newClientBuilder()
                .credentialsProvider(credentialsProvider)
                .gatewayAddress(ZEEBE_ADDRESS)
                .build();
    }

    public static void registerJobWorkers() throws Exception {
        client.newWorker()
                .jobType("CalculateCvScore")
                .handler(new CalculateCvScoreHandler())
                .timeout(Duration.ofSeconds(10).toMillis())
                .open();

        restJobWorkers.scheduleInterview();

//        client.newWorker()
//                .jobType("CalculateCvScore")
//                .handler(new CalculateCvScoreHandler())
//                .timeout(Duration.ofSeconds(10).toMillis())
//                .open();
//
//        client.newWorker()
//                .jobType("CalculateCvScore")
//                .handler(new CalculateCvScoreHandler())
//                .timeout(Duration.ofSeconds(10).toMillis())
//                .open();
//
//        client.newWorker()
//                .jobType("CalculateCvScore")
//                .handler(new CalculateCvScoreHandler())
//                .timeout(Duration.ofSeconds(10).toMillis())
//                .open();
//
//        client.newWorker()
//                .jobType("CalculateCvScore")
//                .handler(new CalculateCvScoreHandler())
//                .timeout(Duration.ofSeconds(10).toMillis())
//                .open();
//
//        client.newWorker()
//                .jobType("CalculateCvScore")
//                .handler(new CalculateCvScoreHandler())
//                .timeout(Duration.ofSeconds(10).toMillis())
//                .open();
    }

    public static void startProcessInstance(JobApplication jobApplication) {
        client.newCreateInstanceCommand()
                .bpmnProcessId("Process_Hiring")
                .latestVersion()
                .variables(jobApplication)
                .send()
                .join();
    }
}
