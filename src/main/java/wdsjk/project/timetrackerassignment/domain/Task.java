package wdsjk.project.timetrackerassignment.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @Column(nullable = false)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Date startedAt;

    private Date finishedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Task(String id, String name, Date startedAt) {
        this.id = id;
        this.name = name;
        this.startedAt = startedAt;
    }
}
