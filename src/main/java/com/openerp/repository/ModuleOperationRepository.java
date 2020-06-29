package com.openerp.repository;

import com.openerp.entity.Module;
import com.openerp.entity.ModuleOperation;
import com.openerp.entity.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModuleOperationRepository extends JpaRepository<ModuleOperation, Integer> {
    List<ModuleOperation> getModuleOperationsByOperation_Active(boolean active);
    List<ModuleOperation> getModuleOperationsByModule_Active(boolean active);
    List<ModuleOperation> getModuleOperationsByModule_ActiveAndOperation_Active(boolean moduleActive, boolean operationActive);
    List<ModuleOperation> getModuleOperationsByModuleIn(List<Module> modules);
    List<ModuleOperation> getModuleOperationsByModuleAndOperation(Module module, Operation operation);

}