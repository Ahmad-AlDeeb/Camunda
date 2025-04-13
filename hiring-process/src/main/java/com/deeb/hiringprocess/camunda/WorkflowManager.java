package com.deeb.hiringprocess.camunda;

import com.deeb.hiringprocess.camunda.handler.CalculateCvScoreHandler;
import com.deeb.hiringprocess.camunda.handler.SaveApplicationHandler;
import com.deeb.hiringprocess.camunda.handler.UpdateApplicationHandler;
import com.deeb.hiringprocess.entity.JobApplication;
import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.worker.JobWorker;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProvider;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProviderBuilder;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Component
public class WorkflowManager {
    @Value("${ZEEBE_AUTHORIZATION_SERVER_URL}")
    private String zeebeAuthorizationServerUrl;

    @Value("${ZEEBE_REST_ADDRESS}")
    private URI zeebeRestAddress;

    @Value("${ZEEBE_GRPC_ADDRESS}")
    private URI zeebeGrpcAddress;

    @Value("${ZEEBE_TOKEN_AUDIENCE}")
    private String zeebeTokenAudience;

    @Value("${ZEEBE_CLIENT_ID}")
    private String zeebeClientId;

    @Value("${ZEEBE_CLIENT_SECRET}")
    private String zeebeClientSecret;

    private ZeebeClient client;
    private final UserTaskWorkers userTaskWorkers;
    private final List<JobWorker> jobWorkers = new ArrayList<>();

    public WorkflowManager(UserTaskWorkers userTaskWorkers) {
        this.userTaskWorkers = userTaskWorkers;
    }

    @PostConstruct
    public void registerWorkers() throws Exception {
        OAuthCredentialsProvider credentialsProvider = new OAuthCredentialsProviderBuilder()
                .authorizationServerUrl(zeebeAuthorizationServerUrl)
                .audience(zeebeTokenAudience)
                .clientId(zeebeClientId)
                .clientSecret(zeebeClientSecret)
                .build();
        client = ZeebeClient.newClientBuilder()
                .credentialsProvider(credentialsProvider)
                .restAddress(zeebeRestAddress)
                .grpcAddress(zeebeGrpcAddress)
                .build();

        jobWorkers.add(client.newWorker()
                .jobType("CalculateCvScore")
                .handler(new CalculateCvScoreHandler())
                .timeout(Duration.ofSeconds(10).toMillis())
                .open());

        jobWorkers.add(client.newWorker()
                .jobType("SaveApplication")
                .handler(new SaveApplicationHandler())
                .timeout(Duration.ofSeconds(10).toMillis())
                .open());

        jobWorkers.add(client.newWorker()
                .jobType("UpdateApplication")
                .handler(new UpdateApplicationHandler())
                .timeout(Duration.ofSeconds(10).toMillis())
                .open());

        userTaskWorkers.scheduleInterview();
        userTaskWorkers.doInterview();
        userTaskWorkers.submitApplicantResponse();
    }

    public void startProcessInstance(JobApplication jobApplication) {
        client.newCreateInstanceCommand()
                .bpmnProcessId("Process_Hiring")
                .latestVersion()
                .variables(jobApplication)
                .send()
                .join();
    }

    @PreDestroy
    public void cleanup() {
        jobWorkers.forEach(JobWorker::close);
        if (client != null) {
            client.close();
        }
    }
}
