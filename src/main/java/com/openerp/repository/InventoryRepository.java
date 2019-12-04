package com.openerp.repository;

import com.openerp.entity.Inventory;
import com.openerp.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Integer> {
    List<Inventory> getInventoriesByActiveTrue();
    List<Inventory> getInventoriesByActiveTrueAndAction_Organization_Id(int id);
    Inventory getInventoryById(int id);
}