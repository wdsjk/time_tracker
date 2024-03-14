package wdsjk.project.timetrackerassignment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import wdsjk.project.timetrackerassignment.dto.ShowTasksTimeRequest;
import wdsjk.project.timetrackerassignment.dto.ShowTasksTimeResponse;

import wdsjk.project.timetrackerassignment.service.TaskService;
import wdsjk.project.timetrackerassignment.service.UserService;

import java.util.Collection;

@RestController
@RequestMapping("/api/v1/summary")
@RequiredArgsConstructor
public class SummaryController {
    private final TaskService taskService;
    private final UserService userService;

    @GetMapping("/task-time")
    public ResponseEntity<Collection<ShowTasksTimeResponse>> showTasksTime(@Valid @RequestBody ShowTasksTimeRequest request) {
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(
                userService.getSummary(request)
        );
    }
}
