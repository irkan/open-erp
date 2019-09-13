package com.openerp.repository;

import com.openerp.entity.Module;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModuleRepository extends JpaRepository<Module, Integer> {
    List<Module> findAllByModuleIsNull();
}