package com.openerp.repository;

import com.openerp.entity.Vacation;
import com.openerp.entity.VacationDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VacationDetailRepository extends JpaRepository<VacationDetail, Integer> {
    List<VacationDetail> getVacationDetailsByVacation_Id(int vacationId);
}