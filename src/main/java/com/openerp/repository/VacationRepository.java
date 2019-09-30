package com.openerp.repository;

import com.openerp.entity.BusinessTrip;
import com.openerp.entity.Vacation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VacationRepository extends JpaRepository<Vacation, Integer> {
}