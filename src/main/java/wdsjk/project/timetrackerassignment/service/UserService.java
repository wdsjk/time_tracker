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

import java.time.Duration;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public String createNewUser(NewUserRequest request) {
        userRepository.save(new User(
                UUID.randomUUID().toString(),
                request.getUsername()
        ));

        log.info("INFO: User with username: %s successfully created".formatted(request.getUsername()));
        return "User successfully created";
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
    public void addTaskToUser(Task task, User user) {
        if (user.getTasks().isEmpty()) {
            user.setTasks(List.of(task));
        } else {
            user.getTasks().add(task);
        }
    }

    public Collection<ShowTaskTimeResponse> getSummary(TimeRequest request) {
        User user = userRepository.findUserByUsername(request.getUsername()).orElseThrow(
                () -> {
                    log.error("ERROR: User with username: %s is not found".formatted(request.getUsername()));
                    return new UserNotFoundException("User with username: %s is not found".
                            formatted(request.getUsername()));
                }
        );

        log.info("INFO: Summary %s's time spent on tasks successfully obtained".formatted(user.getUsername()));
        return user.getTasks().stream()
            .filter(task ->
                    null != task.getFinishedAt() &&
                    task.getStartedAt().after(request.getFrom()) &&
                    task.getFinishedAt().before(request.getTo()))
            .map(
                    task -> new ShowTaskTimeResponse(
                            task.getName(),
                            Duration.between(task.getStartedAt().toInstant(), task.getFinishedAt().toInstant()).toHoursPart() +
                            ":" +
                            Duration.between(task.getStartedAt().toInstant(), task.getFinishedAt().toInstant()).toMinutesPart())
            ).sorted(
                    Comparator.comparing(ShowTaskTimeResponse::spentTime)
            ).toList().reversed();
    }

    public Collection<ShowTimeTaskResponse> getWorkingHours(TimeRequest request) {
        // TODO: to do
        return null;
    }
}
