package com.openerp.repository;

import com.openerp.entity.MigrationUser;
import com.openerp.entity.MigrationUserModuleOperation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MigrationUserModuleOperationRepository extends JpaRepository<MigrationUserModuleOperation, Integer> {
}