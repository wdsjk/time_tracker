package wdsjk.project.timetrackerassignment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import wdsjk.project.timetrackerassignment.dto.NewUserRequest;
import wdsjk.project.timetrackerassignment.dto.UpdateUserRequest;
import wdsjk.project.timetrackerassignment.service.UserService;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@Valid @RequestBody NewUserRequest request) {
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(
                userService.createNewUser(request));
    }

    @PatchMapping("/update")
    public ResponseEntity<String> updateUser(@Valid @RequestBody UpdateUserRequest request) {
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(
                userService.updateUser(request));
    }
}
