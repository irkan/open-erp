package com.openerp.repository;

import com.openerp.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface SalaryEmployeeRepository extends JpaRepository<SalaryEmployee, Integer> {
    List<SalaryEmployee> getSalaryEmployeesBySalary_ActiveAndEmployee_IdOrderByEmployeeDesc(boolean salaryActive, int employeeId);
}