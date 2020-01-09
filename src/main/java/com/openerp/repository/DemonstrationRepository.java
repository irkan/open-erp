package com.openerp.repository;

import com.openerp.entity.Advance;
import com.openerp.entity.Demonstration;
import com.openerp.entity.Organization;
import com.openerp.entity.Sales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface DemonstrationRepository extends JpaRepository<Demonstration, Integer>, JpaSpecificationExecutor<Demonstration> {
    List<Demonstration> getDemonstrationsByActiveTrueOrderByDemonstrateDateDesc();
    List<Demonstration> getDemonstrationsByActiveTrueAndOrganizationOrderByDemonstrateDateDesc(Organization organization);
    Demonstration getDemonstrationById(int id);
}