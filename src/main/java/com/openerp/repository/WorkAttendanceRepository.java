package com.openerp.repository;

import com.openerp.entity.NonWorkingDay;
import com.openerp.entity.WorkAttendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkAttendanceRepository extends JpaRepository<WorkAttendance, Integer> {
}