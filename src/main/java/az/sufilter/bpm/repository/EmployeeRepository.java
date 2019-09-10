package az.sufilter.bpm.repository;

import az.sufilter.bpm.entity.Employee;
import az.sufilter.bpm.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    List<Employee> getEmployeesByOrganization_Id(int organizationId);
}