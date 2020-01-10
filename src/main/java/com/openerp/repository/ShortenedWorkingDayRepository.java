package com.openerp.repository;

import com.openerp.entity.NonWorkingDay;
import com.openerp.entity.ShortenedWorkingDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Date;
import java.util.List;

public interface ShortenedWorkingDayRepository extends JpaRepository<ShortenedWorkingDay, Integer>, JpaSpecificationExecutor<ShortenedWorkingDay> {
    ShortenedWorkingDay getShortenedWorkingDayById(int id);
    List<ShortenedWorkingDay> getShortenedWorkingDaysByActiveTrue();
    List<ShortenedWorkingDay> getShortenedWorkingDaysByWorkingDate(Date date);
}