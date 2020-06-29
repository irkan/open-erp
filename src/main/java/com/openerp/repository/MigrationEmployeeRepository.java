package com.openerp.repository;

import com.openerp.entity.GlobalConfiguration;
import com.openerp.entity.MigrationEmployee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MigrationEmployeeRepository extends JpaRepository<MigrationEmployee, Integer> {
}