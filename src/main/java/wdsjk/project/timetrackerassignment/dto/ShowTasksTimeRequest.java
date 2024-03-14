package wdsjk.project.timetrackerassignment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Date;

@Data
public class ShowTasksTimeRequest {
    @NotBlank(message = "Username can't be blank!")
    private String username;

    private Date from;

    private Date to;
}
