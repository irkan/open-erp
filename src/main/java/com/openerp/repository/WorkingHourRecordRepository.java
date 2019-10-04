package com.openerp.repository;

import com.openerp.entity.WorkingHourRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkingHourRecordRepository extends JpaRepository<WorkingHourRecord, Integer> {
}