package com.openerp.repository;

import com.openerp.entity.Salary;
import com.openerp.entity.WorkAttendance;
import com.openerp.entity.WorkingHourRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaryRepository extends JpaRepository<Salary, Integer> {
    Salary getSalaryByActiveTrueAndWorkingHourRecord(WorkingHourRecord workingHourRecord);
}