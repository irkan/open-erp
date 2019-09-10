package az.sufilter.bpm.repository;

import az.sufilter.bpm.entity.TemplateModuleOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface TemplateModuleOperationRepository extends JpaRepository<TemplateModuleOperation, Integer> {
}