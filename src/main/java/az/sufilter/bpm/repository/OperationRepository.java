package az.sufilter.bpm.repository;

import az.sufilter.bpm.entity.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface OperationRepository extends JpaRepository<Operation, Integer> {
}