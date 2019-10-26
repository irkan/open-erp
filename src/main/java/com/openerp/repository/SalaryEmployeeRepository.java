package com.openerp.repository;

import com.openerp.entity.Salary;
import com.openerp.entity.SalaryEmployee;
import com.openerp.entity.WorkingHourRecord;
import com.openerp.entity.WorkingHourRecordEmployee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalaryEmployeeRepository extends JpaRepository<SalaryEmployee, Integer> {
    List<SalaryEmployee> getSalaryEmployeesByEmployee_IdOrderByEmployeeDesc(int employeeId);
}