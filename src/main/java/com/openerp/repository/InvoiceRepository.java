package com.openerp.repository;

import com.openerp.entity.Sales;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Sales, Integer> {
    List<Sales> getSalesByActiveTrue();
}