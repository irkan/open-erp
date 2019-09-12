package az.sufilter.bpm.repository;

import az.sufilter.bpm.entity.TemplateModuleOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TemplateModuleOperationRepository extends JpaRepository<TemplateModuleOperation, Integer> {
    List<TemplateModuleOperation> findAllByTemplate_Id(int templateId);
}