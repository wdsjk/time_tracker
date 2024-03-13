package wdsjk.project.timetrackerassignment.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import wdsjk.project.timetrackerassignment.domain.User;
import wdsjk.project.timetrackerassignment.dto.NewUserRequest;
import wdsjk.project.timetrackerassignment.dto.UpdateUserRequest;
import wdsjk.project.timetrackerassignment.exception.UserNotFoundException;
import wdsjk.project.timetrackerassignment.repository.UserRepository;

import java.util.UUID;

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
}
