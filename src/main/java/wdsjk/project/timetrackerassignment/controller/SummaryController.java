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

import wdsjk.project.timetrackerassignment.dto.TimeRequest;
import wdsjk.project.timetrackerassignment.dto.ShowTaskTimeResponse;

import wdsjk.project.timetrackerassignment.dto.ShowTimeTaskResponse;
import wdsjk.project.timetrackerassignment.dto.WorkedResponse;
import wdsjk.project.timetrackerassignment.service.UserService;

import java.util.Collection;

@RestController
@RequestMapping("/api/v1/summary")
@RequiredArgsConstructor
public class SummaryController {
    private final UserService userService;

    @GetMapping("/task-time")
    public ResponseEntity<Collection<ShowTaskTimeResponse>> showTaskTime(@Valid @RequestBody TimeRequest request) {
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(
                userService.getSummary(request)
        );
    }

    @GetMapping("/time-task")
    public ResponseEntity<Collection<ShowTimeTaskResponse>> showTimeTask(@Valid @RequestBody TimeRequest request) {
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(
                userService.getWorkingHours(request)
        );
    }

    @GetMapping("/worked")
    public ResponseEntity<WorkedResponse> showWorked (@Valid @RequestBody TimeRequest request) {
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(
                userService.getWorked(request)
        );
    }
}
