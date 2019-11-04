package com.openerp.repository;

import com.openerp.entity.SaleGroup;
import com.openerp.entity.Sales;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesRepository extends JpaRepository<Sales, Integer> {
}