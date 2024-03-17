package wdsjk.project.timetrackerassignment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import wdsjk.project.timetrackerassignment.dto.UserRequest;
import wdsjk.project.timetrackerassignment.dto.UpdateUserRequest;
import wdsjk.project.timetrackerassignment.service.impl.UserServiceImpl;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@Valid @RequestBody UserRequest request) {
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(
                userService.createUser(request));
    }

    @PatchMapping("/update")
    public ResponseEntity<String> updateUser(@Valid @RequestBody UpdateUserRequest request) {
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(
                userService.updateUser(request));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@Valid @RequestBody UserRequest request) {
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(
                userService.deleteUser(request)
        );
    }

    @DeleteMapping("/delete-tracking")
    public ResponseEntity<String> deleteUserTracking(@Valid @RequestBody UserRequest request) {
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(
                userService.deleteTracking(request)
        );
    }
}
