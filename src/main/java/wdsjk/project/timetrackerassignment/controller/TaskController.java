package wdsjk.project.timetrackerassignment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import wdsjk.project.timetrackerassignment.dto.task.StartTaskRequest;
import wdsjk.project.timetrackerassignment.dto.task.FinishTaskRequest;
import wdsjk.project.timetrackerassignment.service.TaskService;

@RestController
@RequestMapping("/api/v1/task")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping("/start")
    public ResponseEntity<String> startTask(@Valid @RequestBody StartTaskRequest request) {
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(
                taskService.startTask(request));
    }

    @PostMapping("/finish")
    public ResponseEntity<String> finishTask(@Valid @RequestBody FinishTaskRequest request) {
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(
                taskService.finishTask(request));
    }
}
