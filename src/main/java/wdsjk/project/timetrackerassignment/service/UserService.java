package wdsjk.project.timetrackerassignment.service;

import wdsjk.project.timetrackerassignment.domain.Task;
import wdsjk.project.timetrackerassignment.domain.User;

import wdsjk.project.timetrackerassignment.dto.time.ShowTaskTimeResponse;
import wdsjk.project.timetrackerassignment.dto.time.ShowTimeTaskResponse;
import wdsjk.project.timetrackerassignment.dto.time.TimeRequest;
import wdsjk.project.timetrackerassignment.dto.time.WorkedResponse;
import wdsjk.project.timetrackerassignment.dto.user.UpdateUserRequest;
import wdsjk.project.timetrackerassignment.dto.user.UserRequest;

import java.util.Collection;

public interface UserService {
    String createUser(UserRequest request);

    User getUserByUsername(String username);

    String updateUser(UpdateUserRequest request);

    void addTaskToUser(Task task, User user);

    String deleteUser(UserRequest request);

    String deleteTracking(UserRequest request);

    Collection<ShowTaskTimeResponse> getSummary(TimeRequest request);

    Collection<ShowTimeTaskResponse> getWorkingHours(TimeRequest request);

    WorkedResponse getWorked(TimeRequest request);
}
