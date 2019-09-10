package az.sufilter.bpm.repository;

import az.sufilter.bpm.entity.User;
import az.sufilter.bpm.entity.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailRepository extends JpaRepository<UserDetail, Integer> {
}