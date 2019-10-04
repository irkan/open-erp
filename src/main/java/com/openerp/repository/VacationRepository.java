package com.openerp.repository;

import com.openerp.entity.BusinessTrip;
import com.openerp.entity.Vacation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VacationRepository extends JpaRepository<Vacation, Integer> {
    List<Vacation> getVacationsByActiveTrue();
    Vacation getVacationById(int id);
}