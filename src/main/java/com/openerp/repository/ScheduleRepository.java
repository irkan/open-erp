package com.openerp.repository;

import com.openerp.entity.Sales;
import com.openerp.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
}