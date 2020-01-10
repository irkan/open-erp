package com.openerp.repository;

import com.openerp.entity.BusinessTrip;
import com.openerp.entity.Employee;
import com.openerp.entity.Notification;
import com.openerp.entity.Vacation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

public interface VacationRepository extends JpaRepository<Vacation, Integer>, JpaSpecificationExecutor<Vacation> {
    List<Vacation> getVacationsByActiveTrue();
    Vacation getVacationById(int id);
}