package com.openerp.repository;

import com.openerp.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Date;
import java.util.List;

public interface SalesRepository extends JpaRepository<Sales, Integer>, JpaSpecificationExecutor<Sales> {
    List<Sales> getSalesByActiveTrueAndServiceFalseOrderByIdDesc();
    List<Sales> getSalesByActiveTrueAndServiceFalseAndApproveTrueAndNotServiceNextFalseOrderByIdAsc();
    List<Sales> getSalesByActiveTrueAndServiceFalseAndOrganizationOrderByIdDesc(Organization organization);
    List<Sales> getSalesByActiveTrueAndServiceTrueOrderByIdDesc();
    List<Sales> getSalesByActiveTrueAndOrganizationOrderByIdDesc(Organization organization);
    Sales getSalesByIdAndActiveTrue(int salesId);
    Sales getSalesById(int salesId);
    List<Sales> getSalesByActiveTrueAndApproveTrueAndPayment_CashAndPayment_PeriodAndSaledFalse(Boolean cash, Dictionary paymentPeriod);
    List<Sales> getSalesByActiveTrueAndApproveTrueAndPayment_CashAndPayment_PeriodAndSaledFalseAndSaleDateGreaterThanEqual(Boolean cash, Dictionary paymentPeriod, Date saleDate);
    List<Sales> getSalesByActiveTrueAndApproveTrueAndPayment_CashAndPayment_PeriodAndSaledFalseAndOrganization(Boolean cash, Dictionary paymentPeriod, Organization organization);
    List<Sales> getSalesByActiveTrueAndApproveTrueAndPayment_CashAndPayment_PeriodAndSaledFalseAndSaleDateGreaterThanEqualAndOrganization(Boolean cash, Dictionary paymentPeriod, Date saleDate, Organization organization);
    List<Sales> getSalesByActiveTrueAndApproveTrueAndPayment_CashAndSaleDateAndSaledFalse(Boolean cash, Date saleDate);
    List<Sales> getSalesByActiveTrueAndApproveTrueAndPayment_CashAndSaleDateAndSaledFalseAndOrganization(Boolean cash, Date saleDate, Organization organization);
    List<Sales> getSalesByActiveTrueAndApproveTrueAndServiceFalseAndCustomerAndOrganizationOrderByIdDesc(Customer customer, Organization organization);
    List<Sales> getSalesByActiveTrueAndApproveTrueAndSaledFalseAndIdNotInOrderByApproveDateAsc(List<Integer> ids);
    List<Sales> getSalesByActiveTrueAndApproveTrueAndSaledFalseAndTaxConfiguration(TaxConfiguration taxConfiguration);
}