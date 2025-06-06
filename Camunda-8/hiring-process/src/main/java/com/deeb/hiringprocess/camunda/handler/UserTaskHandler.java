package com.deeb.hiringprocess.camunda.handler;

import com.deeb.hiringprocess.camunda.client.RestZeebeClient;
import com.deeb.hiringprocess.camunda.client.TasklistClient;
import com.deeb.hiringprocess.camunda.task.Task;
import com.deeb.hiringprocess.camunda.task.TaskState;
import com.deeb.hiringprocess.camunda.util.RequestBodyBuilder;
import com.deeb.hiringprocess.service.JobApplicationService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static java.lang.Long.parseLong;

@Component
public class UserTaskHandler {
    private final RestZeebeClient restZeebeClient;
    private final TasklistClient tasklist;
    private final JobApplicationService jobApplicationService;

    public UserTaskHandler(RestZeebeClient restZeebeClient, TasklistClient tasklist,
                           JobApplicationService jobApplicationService) {
        this.restZeebeClient = restZeebeClient;
        this.tasklist = tasklist;
        this.jobApplicationService = jobApplicationService;
    }

    @Async
    public void scheduleInterview() {
        final String taskDefinitionId = "Activity_ScheduleInterview";
        Map<String, Object> requestBody = RequestBodyBuilder.searchTasks(taskDefinitionId);

        Set<String> completedTasks = new HashSet<>();
        while (true) {
            List<Task> tasks = tasklist.searchTasks(requestBody);

            for (Task task : tasks) {
                if (completedTasks.contains(task.id())
                        || task.taskState() != TaskState.CREATED
                        || !Objects.equals(task.taskDefinitionId(), taskDefinitionId)) {
                    continue;
                }
                completedTasks.add(task.id());

                String isInterested = jobApplicationService.scheduleInterview();
                requestBody = RequestBodyBuilder.completeJob(Map.of("isInterested", isInterested));
                restZeebeClient.completeUserTask(parseLong(task.id()), requestBody);
            }
        }
    }

    @Async
    public void doInterview() {
        final String taskDefinitionId = "Activity_DoInterview";
        Map<String, Object> requestBody = RequestBodyBuilder.searchTasks(taskDefinitionId);

        Set<String> completedTasks = new HashSet<>();
        while (true) {
            List<Task> tasks = tasklist.searchTasks(requestBody);

            for (Task task : tasks) {
                if (completedTasks.contains(task.id())
                        || task.taskState() != TaskState.CREATED
                        || !Objects.equals(task.taskDefinitionId(), taskDefinitionId)) {
                    continue;
                }
                completedTasks.add(task.id());

                String isFit = jobApplicationService.doInterview();
                requestBody = RequestBodyBuilder.completeJob(Map.of("isFit", isFit));
                restZeebeClient.completeUserTask(parseLong(task.id()), requestBody);
            }
        }
    }

    @Async
    public void submitApplicantResponse() {
        final String taskDefinitionId = "Activity_SubmitApplicantResponse";
        Map<String, Object> requestBody = RequestBodyBuilder.searchTasks(taskDefinitionId);

        Set<String> completedTasks = new HashSet<>();
        while (true) {
            List<Task> tasks = tasklist.searchTasks(requestBody);

            for (Task task : tasks) {
                if (completedTasks.contains(task.id())
                        || task.taskState() != TaskState.CREATED
                        || !Objects.equals(task.taskDefinitionId(), taskDefinitionId)) {
                    continue;
                }
                completedTasks.add(task.id());

                String isOfferAccepted = jobApplicationService.submitApplicantResponse();
                requestBody = RequestBodyBuilder.completeJob(Map.of("isOfferAccepted", isOfferAccepted));
                restZeebeClient.completeUserTask(parseLong(task.id()), requestBody);
            }
        }
    }
}
