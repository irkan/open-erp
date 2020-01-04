package com.openerp.repository;

import com.openerp.entity.Sales;
import com.openerp.entity.SalesInventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalesInventoryRepository extends JpaRepository<SalesInventory, Integer> {
    List<SalesInventory> getSalesInventoriesByActiveTrueAndSales(Sales sales);
}