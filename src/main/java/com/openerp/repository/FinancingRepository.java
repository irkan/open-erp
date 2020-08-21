package com.openerp.repository;

import com.openerp.entity.Financing;
import com.openerp.entity.Inventory;
import com.openerp.entity.Notification;
import com.openerp.entity.Organization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface FinancingRepository extends JpaRepository<Financing, Integer>, JpaSpecificationExecutor<Financing> {
    List<Financing> getFinancingsByActiveTrueOrderByIdDesc();
    List<Financing> getFinancingsByActiveTrueAndOrganizationOrderByIdDesc(Organization organization);
    List<Financing> getFinancingsByActiveTrueAndInventoryAndOrganizationOrderByIdDesc(Inventory inventory, Organization organization);
    Financing getFinancingById(int id);
    List<Financing> getFinancingByActiveTrueAndInventory_BarcodeAndOrganizationOrderByIdAsc(String barcode, Organization organization);
    Financing getFinancingByActiveTrueAndInventoryAndOrganization(Inventory inventory, Organization organization);
}