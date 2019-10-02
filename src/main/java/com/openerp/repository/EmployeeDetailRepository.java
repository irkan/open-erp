package com.openerp.repository;

import com.openerp.entity.EmployeeDetail;
import com.openerp.entity.ModuleOperation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeDetailRepository extends JpaRepository<EmployeeDetail, Integer> {
}