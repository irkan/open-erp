package com.openerp.repository;

import com.openerp.entity.Organization;
import com.openerp.entity.Sales;
import com.openerp.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface SalesRepository extends JpaRepository<Sales, Integer>, JpaSpecificationExecutor<Sales> {
    List<Sales> getSalesByActiveTrueAndServiceFalseOrderByIdDesc();
    List<Sales> getSalesByActiveTrueAndServiceFalseAndApproveTrueOrderByIdAsc();
    List<Sales> getSalesByActiveTrueAndServiceFalseAndOrganizationOrderByIdDesc(Organization organization);
    List<Sales> getSalesByActiveTrueAndServiceTrueOrderByIdDesc();
    List<Sales> getSalesByActiveTrueAndOrganizationOrderByIdDesc(Organization organization);
    Sales getSalesByIdAndActiveTrue(int salesId);
    Sales getSalesById(int salesId);
}