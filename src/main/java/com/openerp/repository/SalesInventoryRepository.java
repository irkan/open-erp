package com.openerp.repository;

import com.openerp.entity.SalesInventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesInventoryRepository extends JpaRepository<SalesInventory, Integer> {
}