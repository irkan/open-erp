package com.openerp.repository;

import com.openerp.entity.Employee;
import com.openerp.entity.EmployeeRestDay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRestDayRepository extends JpaRepository<EmployeeRestDay, Integer> {
    List<EmployeeRestDay> getEmployeeRestDaysByEmployee(Employee employee);
    List<EmployeeRestDay> getEmployeeRestDaysByEmployeeAndDay(Employee employee, int day);
}