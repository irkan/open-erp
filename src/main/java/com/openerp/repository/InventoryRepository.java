package com.openerp.repository;

import com.openerp.entity.Inventory;
import com.openerp.entity.Organization;
import com.openerp.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Integer> {
    List<Inventory> getInventoriesByActiveTrueOrderByIdDesc();
    List<Inventory> findDistinctByActiveTrueAndActions_Organization_IdOrActions_FromOrganization_IdOrderByIdDesc(int organizationId, int fromOrganizationId);
    List<Inventory> findDistinctByActiveTrueAndActions_FromOrganization_IdOrderByIdDesc(int id);
    Inventory getInventoryById(int id);
    Inventory getInventoryByBarcodeAndActiveTrue(String barcode);
}