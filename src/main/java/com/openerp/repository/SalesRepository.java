package com.openerp.repository;

import com.openerp.entity.Organization;
import com.openerp.entity.Sales;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalesRepository extends JpaRepository<Sales, Integer> {
    List<Sales> getSalesByActiveTrueOrderByIdDesc();
    List<Sales> getSalesByActiveTrueAndOrganizationOrderByIdDesc(Organization organization);
    Sales getSalesByIdAndActiveTrue(int salesId);
}