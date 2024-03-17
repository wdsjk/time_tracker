package wdsjk.project.timetrackerassignment.service;

import wdsjk.project.timetrackerassignment.domain.Task;
import wdsjk.project.timetrackerassignment.domain.User;

import wdsjk.project.timetrackerassignment.dto.task.FinishTaskRequest;
import wdsjk.project.timetrackerassignment.dto.task.StartTaskRequest;

import java.util.Date;

public interface TaskService {
    String startTask(StartTaskRequest request);

    String finishTask(FinishTaskRequest request);

    void deleteUserTasks(User user);

    String getSpentTimeOnTask(Task task);

    boolean filterTaskByTime(Task task, Date from, Date to);
}
