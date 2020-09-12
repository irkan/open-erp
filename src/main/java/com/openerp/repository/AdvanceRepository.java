package com.openerp.repository;

import com.openerp.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Date;
import java.util.List;

public interface AdvanceRepository extends JpaRepository<Advance, Integer>, JpaSpecificationExecutor<Advance> {
    List<Advance> getAdvancesByActiveTrueOrderByIdDesc();
    List<Advance> getAdvancesByActiveTrueAndSalesId(Integer salesId);
    List<Advance> getAdvancesByActiveTrueAndOrganizationOrderByIdDesc(Organization organization);
    Advance getAdvanceById(int id);
    List<Advance> getAdvancesByActiveTrueAndApproveFalseAndEmployee(Employee employee);
    List<Advance> getAdvancesByActiveTrueAndEmployee(Employee employee);
    List<Advance> getAdvancesByActiveTrueAndApproveTrueAndAdvanceDateBetweenAndEmployee(Date date1, Date date2, Employee employee);
    Advance findTopByActiveTrueAndApproveTrueAndEmployeeOrderByAdvanceDateDesc(Employee employee);
}