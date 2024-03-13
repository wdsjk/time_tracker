package wdsjk.project.timetrackerassignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import wdsjk.project.timetrackerassignment.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    @Query(value = "select * from usr where username=#{#username}", nativeQuery = true)
    Optional<User> findUserByUsername(String username);
}
