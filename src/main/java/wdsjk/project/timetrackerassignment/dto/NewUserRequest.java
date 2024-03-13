package wdsjk.project.timetrackerassignment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class NewUserRequest {
    @NotBlank(message = "Username can't be blank!")
    private String username;
}
