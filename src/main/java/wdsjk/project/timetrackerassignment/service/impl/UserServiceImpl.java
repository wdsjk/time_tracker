package wdsjk.project.timetrackerassignment.service.impl;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import wdsjk.project.timetrackerassignment.domain.Task;
import wdsjk.project.timetrackerassignment.domain.User;

import wdsjk.project.timetrackerassignment.dto.*;

import wdsjk.project.timetrackerassignment.exception.UserNotFoundException;
import wdsjk.project.timetrackerassignment.repository.UserRepository;

import wdsjk.project.timetrackerassignment.service.TaskService;
import wdsjk.project.timetrackerassignment.service.UserService;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final TaskService taskService;

    public UserServiceImpl(UserRepository userRepository, @Lazy TaskService taskService) {
        this.userRepository = userRepository;
        this.taskService = taskService;
    }

    @Override
    public String createUser(UserRequest request) {
        userRepository.save(new User(
                UUID.randomUUID().toString(),
                request.getUsername()
        ));

        log.info("INFO: User with username: %s successfully created".formatted(request.getUsername()));
        return "User successfully created";
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findUserByUsername(username).orElseThrow(
                () -> {
                    log.error("ERROR: User with username: %s is not found".formatted(username));
                    return new UserNotFoundException("User with username: %s is not found".
                            formatted(username));
                }
        );
    }

    @Override
    @Transactional
    public String updateUser(UpdateUserRequest request) {
        User user = userRepository.findUserByUsername(request.getOldUsername()).orElseThrow(
                () -> {
                    log.error("ERROR: User with username: %s is not found".formatted(request.getOldUsername()));
                    return new UserNotFoundException("User with username: %s is not found".
                            formatted(request.getOldUsername()));
                }
        );
        user.setUsername(request.getNewUsername());

        log.info("INFO: Username %s successfully updated to username: %s"
                .formatted(request.getOldUsername(), request.getNewUsername()));
        return "User successfully updated";
    }

    @Override
    @Transactional
    public void addTaskToUser(Task task, User user) {
        if (user.getTasks().isEmpty()) {
            user.setTasks(List.of(task));
        } else {
            user.getTasks().add(task);
        }

        log.info("INFO: Task with name %s successfully added to user %s".formatted(
                task.getName(), user.getUsername()
        ));
    }

    @Override
    @Transactional
    public String deleteUser(UserRequest request) {
        User user = this.getUserByUsername(request.getUsername());

        taskService.deleteUserTasks(user);
        userRepository.delete(user);

        log.info("INFO: User %s successfully deleted".formatted(request.getUsername()));
        return "User %s successfully deleted".formatted(request.getUsername());
    }

    @Override
    @Transactional
    public String deleteTracking(UserRequest request) {
        User user = this.getUserByUsername(request.getUsername());

        taskService.deleteUserTasks(user);

        log.info("INFO: %s's tracking info successfully deleted".formatted(request.getUsername()));
        return "%s's tracking info successfully deleted".formatted(request.getUsername());
    }

    @Override
    public Collection<ShowTaskTimeResponse> getSummary(TimeRequest request) {
        User user = this.getUserByUsername(request.getUsername());

        log.info("INFO: Summary %s's time spent on tasks successfully obtained".formatted(user.getUsername()));
        return user.getTasks().stream()
            .filter(task -> taskService.filterTaskByTime(task, request.getFrom(), request.getTo()))
            .map(
                    task -> new ShowTaskTimeResponse(
                            task.getName(),
                            taskService.getSpentTimeOnTask(task)
                    )
            ).sorted(
                    Comparator.comparing(ShowTaskTimeResponse::spentTime)
            ).toList().reversed();
    }

    @Override
    public Collection<ShowTimeTaskResponse> getWorkingHours(TimeRequest request) {
        User user = this.getUserByUsername(request.getUsername());

        log.info("INFO: Summary %s's time spent in the time from %s to %s successfully obtained".formatted(
                user.getUsername(), request.getFrom(), request.getTo()));
        return user.getTasks().stream()
                .filter(task -> taskService.filterTaskByTime(task, request.getFrom(), request.getTo()))
                .map(
                        task -> new ShowTimeTaskResponse(
                                Arrays.stream(SimpleDateFormat.getDateInstance().format(task.getStartedAt().getTime()).split(","))
                                        .findFirst().orElse(null) // never will be null because of the checking above
                                    + ", " + taskService.getSpentTimeOnTask(task),
                                task.getName()
                        )
                ).toList();
    }

    @Override
    public WorkedResponse getWorked(TimeRequest request) {
        User user = this.getUserByUsername(request.getUsername());

        List<String> timeOnEachTask = user.getTasks().stream()
            .filter(task -> taskService.filterTaskByTime(task, request.getFrom(), request.getTo()))
            .map(taskService::getSpentTimeOnTask)
            .toList();

        int hours = 0;
        int minutes = 0;

        for (String time : timeOnEachTask) {
            hours += Integer.parseInt(time.substring(0, time.indexOf(':')));
            minutes += Integer.parseInt(time.substring(time.indexOf(':')+1));
        }

        log.info("INFO: %s's worked time successfully obtained".formatted(request.getUsername()));
        return new WorkedResponse(hours + ":" + minutes);
    }
}
