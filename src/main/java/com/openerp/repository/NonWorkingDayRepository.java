package com.openerp.repository;

import com.openerp.entity.Employee;
import com.openerp.entity.NonWorkingDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface NonWorkingDayRepository extends JpaRepository<NonWorkingDay, Integer> {
    NonWorkingDay getNonWorkingDayById(int id);
    List<NonWorkingDay> getNonWorkingDaysByNonWorkingDateAndActiveTrue(Date date);
    List<NonWorkingDay> getNonWorkingDaysByActiveTrue();
    @Query(value = "from NonWorkingDay t where t.active=1 and t.nonWorkingDate BETWEEN :startDate AND :endDate")
    List<NonWorkingDay> getNotWorkingDaysByStartDateAndEndDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}