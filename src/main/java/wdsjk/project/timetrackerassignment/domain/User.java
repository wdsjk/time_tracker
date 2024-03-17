package wdsjk.project.timetrackerassignment.domain;

import jakarta.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "usr")
public class User {
    @Id
    @Column(unique = true, nullable = false)
    private String id;

    @Column(unique = true, nullable = false)
    private String username;

    @OneToMany(mappedBy = "user")
    private List<Task> tasks;

    public User(String id, String username) {
        this.id = id;
        this.username = username;
    }
}
