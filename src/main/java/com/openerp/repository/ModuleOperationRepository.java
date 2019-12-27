package com.openerp.repository;

import com.openerp.entity.ModuleOperation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModuleOperationRepository extends JpaRepository<ModuleOperation, Integer> {
    List<ModuleOperation> getModuleOperationsByOperation_Active(boolean active);
    List<ModuleOperation> getModuleOperationsByModule_Active(boolean active);
    List<ModuleOperation> getModuleOperationsByModule_Path(String path);

}