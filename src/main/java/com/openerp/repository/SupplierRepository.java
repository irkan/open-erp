package com.openerp.repository;

import com.openerp.entity.Inventory;
import com.openerp.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
    List<Supplier> getSuppliersByActiveTrue();
    Supplier getSuppliersById(int id);
    List<Supplier> getSuppliersByName(String name);
    List<Supplier> getSuppliersByNameAndActiveTrue(String name);
}