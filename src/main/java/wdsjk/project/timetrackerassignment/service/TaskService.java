package wdsjk.project.timetrackerassignment.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import wdsjk.project.timetrackerassignment.domain.Task;
import wdsjk.project.timetrackerassignment.domain.User;
import wdsjk.project.timetrackerassignment.dto.StartTaskRequest;
import wdsjk.project.timetrackerassignment.dto.StopTaskRequest;
import wdsjk.project.timetrackerassignment.exception.TaskNotFound;
import wdsjk.project.timetrackerassignment.repository.TaskRepository;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService {
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
    public String stopTask(StopTaskRequest request) {
        User user = userService.getUserByUsername(request.getUsername());
        Task task = taskRepository.findTaskByName(request.getName()).orElseThrow(
                () -> new TaskNotFound("Task with name: %s is not found".formatted(request.getName()))
        );

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
}
