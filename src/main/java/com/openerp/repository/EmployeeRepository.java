package com.openerp.repository;

import com.openerp.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    List<Employee> getEmployeesByContractEndDateIsNullAndOrganization_Id(int organizationId);
    List<Employee> getEmployeesByContractEndDateIsNull();
    Employee getEmployeeById(int id);
}