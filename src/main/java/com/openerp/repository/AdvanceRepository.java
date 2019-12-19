package com.openerp.repository;

import com.openerp.entity.Advance;
import com.openerp.entity.Illness;
import com.openerp.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdvanceRepository extends JpaRepository<Advance, Integer> {
    List<Advance> getAdvancesByActiveTrueOrderByIdDesc();
    List<Advance> getAdvancesByActiveTrueAndOrganizationOrderByIdDesc(Organization organization);
    Advance getAdvanceById(int id);
}