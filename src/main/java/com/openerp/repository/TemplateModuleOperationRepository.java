package com.openerp.repository;

import com.openerp.entity.TemplateModuleOperation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TemplateModuleOperationRepository extends JpaRepository<TemplateModuleOperation, Integer> {
    List<TemplateModuleOperation> findAllByTemplate_Id(int templateId);
}