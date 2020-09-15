package com.openerp.repository;

import com.openerp.entity.Employee;
import com.openerp.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer>, JpaSpecificationExecutor<Employee> {
    List<Employee> getEmployeesByContractEndDateIsNullAndOrganizationAndActiveTrue(Organization organization);
    List<Employee> getEmployeesByContractEndDateIsNullAndOrganizationAndActiveTrueAndSalaryTrue(Organization organization);
    List<Employee> getEmployeesByOrganizationAndActiveTrueOrderByContractEndDate(Organization organization);
    List<Employee> getEmployeesByContractEndDateIsNullAndActiveTrue();
    List<Employee> getEmployeesByActiveTrueOrderByContractEndDate();
    Employee getEmployeeById(int id);
    List<Employee> getEmployeesByPersonFirstNameStartingWithAndPersonLastNameStartingWithAndOrganizationAndActiveTrue(String firstName, String lastName, Organization organization);
    List<Employee> getEmployeesByPersonFirstNameStartingWithAndPersonLastNameStartingWithAndActiveTrue(String firstName, String lastName);
    List<Employee> getEmployeesByPersonFirstNameStartingWithAndOrganizationAndActiveTrue(String firstName, Organization organization);
    List<Employee> getEmployeesByPersonFirstNameStartingWithAndActiveTrue(String firstName);
    List<Employee> getEmployeesByPersonFirstNameAndPersonLastNameAndPersonFatherNameAndOrganization(String firstName, String lastName, String fatherName, Organization organization);
    List<Employee> getEmployeesByPersonFirstNameAndPersonLastNameAndOrganization(String firstName, String lastName, Organization organization);
}