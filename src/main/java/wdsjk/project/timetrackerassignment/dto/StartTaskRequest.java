package wdsjk.project.timetrackerassignment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class StartTaskRequest {
    @NotBlank(message = "Username can't be blank!")
    private String username;

    @NotBlank(message = "Task's name can't be blank!")
    private String name;
}
