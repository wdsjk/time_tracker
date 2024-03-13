package wdsjk.project.timetrackerassignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wdsjk.project.timetrackerassignment.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findUserByUsername(String username);
}
