package com.openerp.repository;

import com.openerp.entity.WorkingHourRecordEmployee;
import com.openerp.entity.WorkingHourRecordEmployeeIdentifier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkingHourRecordEmployeeIdentifierRepository extends JpaRepository<WorkingHourRecordEmployeeIdentifier, Integer> {
    List<WorkingHourRecordEmployeeIdentifier> getWorkingHourRecordEmployeeIdentifiersByWorkingHourRecordEmployee(WorkingHourRecordEmployee workingHourRecordEmployee);
}