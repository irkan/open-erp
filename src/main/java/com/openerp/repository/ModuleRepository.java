package com.openerp.repository;

import com.openerp.entity.Module;
import com.openerp.entity.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModuleRepository extends JpaRepository<Module, Integer> {
    List<Module> getModulesByActiveTrueAndModuleIsNull();
    Module getModuleById(int id);
    List<Module> getModulesByActiveTrue();
    List<Module> getModulesByActiveTrueAndPath(String path);
}