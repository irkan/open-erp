package com.openerp.repository;

import com.openerp.entity.Financing;
import com.openerp.entity.Inventory;
import com.openerp.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FinancingRepository extends JpaRepository<Financing, Integer> {
    List<Financing> getFinancingsByActiveTrue();
    Financing getFinancingById(int id);
    Financing getFinancingByActiveTrueAndInventoryAndOrganization(Inventory inventory, Organization organization);
}