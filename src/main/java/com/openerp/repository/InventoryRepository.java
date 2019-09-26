package com.openerp.repository;

import com.openerp.entity.Inventory;
import com.openerp.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Integer> {
}