package wdsjk.project.timetrackerassignment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateUserRequest {
    @NotBlank(message = "Username can't be blank!")
    private String oldUsername;

    @NotBlank(message = "Username can't be blank!")
    private String newUsername;
}
