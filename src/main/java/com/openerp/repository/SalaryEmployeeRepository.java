package com.openerp.repository;

import com.openerp.entity.Salary;
import com.openerp.entity.SalaryEmployee;
import com.openerp.entity.WorkingHourRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaryEmployeeRepository extends JpaRepository<SalaryEmployee, Integer> {
}