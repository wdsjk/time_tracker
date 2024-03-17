package wdsjk.project.timetrackerassignment.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import wdsjk.project.timetrackerassignment.domain.Task;
import wdsjk.project.timetrackerassignment.domain.User;

import wdsjk.project.timetrackerassignment.dto.*;

import wdsjk.project.timetrackerassignment.exception.UserNotFoundException;
import wdsjk.project.timetrackerassignment.repository.UserRepository;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final TaskService taskService;

    public String createNewUser(NewUserRequest request) {
        userRepository.save(new User(
                UUID.randomUUID().toString(),
                request.getUsername()
        ));

        log.info("INFO: User with username: %s successfully created".formatted(request.getUsername()));
        return "User successfully created";
    }

    public User getUserByUsername(String username) {
        return userRepository.findUserByUsername(username).orElseThrow(
                () -> {
                    log.error("ERROR: User with username: %s is not found".formatted(username));
                    return new UserNotFoundException("User with username: %s is not found".
                            formatted(username));
                }
        );
    }

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

    @Transactional
    public void addTaskToUser(Task task, User user) {
        if (user.getTasks().isEmpty()) {
            user.setTasks(List.of(task));
        } else {
            user.getTasks().add(task);
        }
    }

    public Collection<ShowTaskTimeResponse> getSummary(TimeRequest request) {
        User user = this.getUserByUsername(request.getUsername());

        log.info("INFO: Summary %s's time spent on tasks successfully obtained".formatted(user.getUsername()));
        return user.getTasks().stream()
            .filter(task -> taskService.filterTaskByTime(task, request.getFrom(), request.getTo()))
            .map(
                    task -> new ShowTaskTimeResponse(
                            task.getName(),
                            taskService.getSpentTimeOnTask(task.getName())
                    )
            ).sorted(
                    Comparator.comparing(ShowTaskTimeResponse::spentTime)
            ).toList().reversed();
    }

    public Collection<ShowTimeTaskResponse> getWorkingHours(TimeRequest request) {
        User user = this.getUserByUsername(request.getUsername());

        log.info("INFO: Summary %s's time spent in the time from %s to %s successfully obtained"
                .formatted(
                        user.getUsername(),
                        request.getFrom(),
                        request.getTo()
                )
        );
        return user.getTasks().stream()
                .filter(task -> taskService.filterTaskByTime(task, request.getFrom(), request.getTo()))
                .map(
                        task -> new ShowTimeTaskResponse(
                                Arrays.stream(SimpleDateFormat.getDateInstance().format(task.getStartedAt().getTime()).split(","))
                                        .findFirst().orElse(null) // never will be null because of the checking above
                                    + ", " + taskService.getSpentTimeOnTask(task.getName()),
                                task.getName()
                        )
                ).toList();
    }

    public WorkedResponse getWorked(TimeRequest request) {
        User user = this.getUserByUsername(request.getUsername());

        List<String> timeOnEachTask = user.getTasks().stream()
            .filter(task -> taskService.filterTaskByTime(task, request.getFrom(), request.getTo()))
            .map(task -> taskService.getSpentTimeOnTask(task.getName()))
            .toList();

        int hours = 0;
        int minutes = 0;

        for (String time : timeOnEachTask) {
            hours += Integer.parseInt(time.substring(0, time.indexOf(':')));
            minutes += Integer.parseInt(time.substring(time.indexOf(':')+1));
        }

        return new WorkedResponse(hours + ":" + minutes);
    }
}
