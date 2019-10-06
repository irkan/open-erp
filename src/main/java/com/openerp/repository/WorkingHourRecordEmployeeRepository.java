package com.openerp.repository;

import com.openerp.entity.WorkingHourRecordEmployee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkingHourRecordEmployeeRepository extends JpaRepository<WorkingHourRecordEmployee, Integer> {
    List<WorkingHourRecordEmployee> getWorkingHourRecordEmployeesByWorkingHourRecord_Id(int workingHourRecordId);
}