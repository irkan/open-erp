package com.openerp.repository;

import com.openerp.entity.BusinessTrip;
import com.openerp.entity.Employee;
import com.openerp.entity.Vacation;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

public interface VacationRepository extends JpaRepository<Vacation, Integer> {
    List<Vacation> getVacationsByActiveTrue();
    List<Vacation> getVacationsByActiveTrueAndStartDateAndEndDate(Date startDate, Date endDate);
    Vacation getVacationById(int id);
}