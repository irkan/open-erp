package com.openerp.repository;

import com.openerp.entity.ShortenedWorkingDay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShortenedWorkingDayRepository extends JpaRepository<ShortenedWorkingDay, Integer> {
}