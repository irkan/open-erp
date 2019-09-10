package az.sufilter.bpm.repository;

import az.sufilter.bpm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsernameAndPasswordAndActiveTrue(String username, String password);
}