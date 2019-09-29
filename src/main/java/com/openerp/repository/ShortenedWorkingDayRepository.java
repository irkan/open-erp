package com.openerp.repository;

import com.openerp.entity.NonWorkingDay;
import com.openerp.entity.ShortenedWorkingDay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ShortenedWorkingDayRepository extends JpaRepository<ShortenedWorkingDay, Integer> {
    ShortenedWorkingDay getShortenedWorkingDayById(int id);
    List<ShortenedWorkingDay> getShortenedWorkingDaysByActiveTrue();
    ShortenedWorkingDay getShortenedWorkingDayByWorkingDate(Date date);
}