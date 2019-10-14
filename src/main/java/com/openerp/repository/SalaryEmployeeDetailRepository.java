package com.openerp.repository;

import com.openerp.entity.SalaryEmployee;
import com.openerp.entity.SalaryEmployeeDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalaryEmployeeDetailRepository extends JpaRepository<SalaryEmployeeDetail, Integer> {
}