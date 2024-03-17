package wdsjk.project.timetrackerassignment.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import wdsjk.project.timetrackerassignment.domain.Task;
import wdsjk.project.timetrackerassignment.domain.User;

import wdsjk.project.timetrackerassignment.dto.StartTaskRequest;
import wdsjk.project.timetrackerassignment.dto.FinishTaskRequest;

import wdsjk.project.timetrackerassignment.exception.TaskAlreadyFinishedException;
import wdsjk.project.timetrackerassignment.exception.TaskNotFoundException;

import wdsjk.project.timetrackerassignment.exception.UserHasNoSuchTaskException;
import wdsjk.project.timetrackerassignment.repository.TaskRepository;

import wdsjk.project.timetrackerassignment.service.TaskService;
import wdsjk.project.timetrackerassignment.service.UserService;

import java.time.Duration;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserService userService;

    public String startTask(StartTaskRequest request) {
        User user = userService.getUserByUsername(request.getUsername());

        Task task = new Task(
                UUID.randomUUID().toString(),
                request.getName(),
                new Date()
        );

        task.setUser(user);
        taskRepository.save(task);

        userService.addTaskToUser(task, user);

        log.info("INFO: Task with name: %s successfully started".formatted(request.getName()));
        return "Task successfully started";
    }

    @Transactional
    public String finishTask(FinishTaskRequest request) {
        User user = userService.getUserByUsername(request.getUsername());
        Task task = taskRepository.findTaskByName(request.getName()).orElseThrow(
                () -> {
                    log.error("ERROR: Task with name: %s is not found!".formatted(request.getName()));
                    return new TaskNotFoundException("Task with name: %s is not found!".formatted(request.getName()));
                }
        );

        if (!(task.getUser().getUsername()).equals(request.getUsername())) {
            log.error("ERROR: User %s hasn't got such task: %s".formatted(request.getUsername(), request.getName()));
            throw new UserHasNoSuchTaskException("User %s hasn't got such task: %s".formatted(
                    request.getUsername(),
                    request.getName()
            ));
        }

        if (null != task.getFinishedAt()) {
            log.error("ERROR: Task %s is already finished!".formatted(task.getName()));
            throw new TaskAlreadyFinishedException("Task %s is already finished!".formatted(task.getName()));
        }

        task.setFinishedAt(new Date());
        user.getTasks().forEach(
                t -> {
                    if (t.getName().equals(task.getName())) {
                        t.setFinishedAt(task.getFinishedAt());
                    }
                }
        );

        log.info("INFO: Task with name: %s successfully finished".formatted(request.getName()));
        return "Task successfully finished";
    }

    @Transactional
    public void deleteUserTasks(User user) {
        log.info("INFO: %s's tasks successfully deleted".formatted(user.getUsername()));
        taskRepository.deleteAllByUserId(user.getId());
    }

    public String getSpentTimeOnTask(Task task) {
        return Duration.between(task.getStartedAt().toInstant(), task.getFinishedAt().toInstant()).toHoursPart()
                + ":" +
                Duration.between(task.getStartedAt().toInstant(), task.getFinishedAt().toInstant()).toMinutesPart();
    }

    public boolean filterTaskByTime(Task task, Date from, Date to) {
        return null != task.getFinishedAt() && task.getStartedAt().after(from) && task.getFinishedAt().before(to);
    }
}
