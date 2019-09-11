package az.sufilter.bpm.repository;

import az.sufilter.bpm.entity.UserModuleOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserModuleOperationRepository extends JpaRepository<UserModuleOperation, Integer> {
    List<UserModuleOperation> findAllByUser_Id(int userId);
}