package az.sufilter.bpm.repository;

import az.sufilter.bpm.entity.ModuleOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ModuleOperationRepository extends JpaRepository<ModuleOperation, Integer> {
}