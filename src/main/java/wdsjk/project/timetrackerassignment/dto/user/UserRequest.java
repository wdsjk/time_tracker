package wdsjk.project.timetrackerassignment.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRequest {
    @NotBlank(message = "Username can't be blank!")
    private String username;
}
