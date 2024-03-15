package wdsjk.project.timetrackerassignment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class TimeRequest {
    @NotBlank(message = "Username can't be blank!")
    private String username;

    @NotNull(message = "The period of time can't be blank!")
    private Date from;

    @NotNull(message = "The period of time can't be blank!")
    private Date to;
}
