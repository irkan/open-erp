package com.openerp.repository;

import com.openerp.entity.Employee;
import com.openerp.entity.Inventory;
import com.openerp.entity.Organization;
import com.openerp.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Integer>, JpaSpecificationExecutor<Inventory> {
    List<Inventory> getInventoriesByActiveTrueOrderByIdDesc();
    List<Inventory> getInventoriesByBarcodeAndOrganization(String barcode, Organization organization);
    List<Inventory> findDistinctByActiveTrueAndActions_Organization_IdOrActions_FromOrganization_IdOrderByIdDesc(int organizationId, int fromOrganizationId);
    List<Inventory> findDistinctByActiveTrueAndActions_FromOrganization_IdOrderByIdDesc(int id);
    Inventory getInventoryById(int id);
    List<Inventory> getInventoriesByActiveTrueAndBarcodeAndOrganizationOrderByIdAsc(String barcode, Organization organization);
    Inventory getInventoryByBarcodeAndActiveTrue(String barcode);
    List<Inventory> getInventoriesByNameAndActiveTrue(String name);
    List<Inventory> getInventoriesByNameAndActiveTrueOrderByInventoryDateDesc(String name);
}