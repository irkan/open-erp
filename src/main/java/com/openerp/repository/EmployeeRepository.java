package com.openerp.repository;

import com.openerp.entity.Employee;
import com.openerp.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer>, JpaSpecificationExecutor<Employee> {
    List<Employee> getEmployeesByContractEndDateIsNullAndOrganizationAndActiveTrue(Organization organization);
    List<Employee> getEmployeesByContractEndDateIsNullAndActiveTrue();
    Employee getEmployeeById(int id);
}