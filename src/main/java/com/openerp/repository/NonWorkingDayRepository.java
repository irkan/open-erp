package com.openerp.repository;

import com.openerp.entity.Employee;
import com.openerp.entity.NonWorkingDay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface NonWorkingDayRepository extends JpaRepository<NonWorkingDay, Integer> {
    NonWorkingDay getNonWorkingDayById(int id);
    NonWorkingDay getNonWorkingDayByNonWorkingDate(Date date);
    List<NonWorkingDay> getNonWorkingDaysByActiveTrue();
}