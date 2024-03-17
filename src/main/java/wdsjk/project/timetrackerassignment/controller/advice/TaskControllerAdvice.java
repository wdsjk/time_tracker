package wdsjk.project.timetrackerassignment.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import wdsjk.project.timetrackerassignment.exception.TaskAlreadyFinishedException;
import wdsjk.project.timetrackerassignment.exception.TaskNotFoundException;
import wdsjk.project.timetrackerassignment.exception.UserHasNoSuchTaskException;

@RestControllerAdvice
public class TaskControllerAdvice {
    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<String> handleTaskNotFound(TaskNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(e.getMessage());
    }

    @ExceptionHandler(TaskAlreadyFinishedException.class)
    public ResponseEntity<String> handleTaskAlreadyFinishedException(TaskAlreadyFinishedException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(e.getMessage());
    }

    @ExceptionHandler(UserHasNoSuchTaskException.class)
    public ResponseEntity<String> handleUserHasNoSuchTaskException(UserHasNoSuchTaskException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(e.getMessage());
    }
}
