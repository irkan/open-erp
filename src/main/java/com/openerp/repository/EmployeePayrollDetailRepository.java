package com.openerp.repository;

import com.openerp.entity.Employee;
import com.openerp.entity.EmployeePayrollDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeePayrollDetailRepository extends JpaRepository<EmployeePayrollDetail, Integer> {
    List<EmployeePayrollDetail> getEmployeePayrollDetailsByEmployee_Id(Integer id);
    EmployeePayrollDetail getEmployeePayrollDetailByEmployeeAndKey(Employee employee, String key);
}