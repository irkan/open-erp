package com.openerp.repository;

import com.openerp.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Date;
import java.util.List;

public interface SalesRepository extends JpaRepository<Sales, Integer>, JpaSpecificationExecutor<Sales> {
    List<Sales> getSalesByActiveTrueAndServiceFalseOrderByIdDesc();
    List<Sales> getSalesByActiveTrueAndServiceFalseAndApproveTrueAndNotServiceNextFalseAndReturnedFalseOrderByIdAsc();
    List<Sales> getSalesByActiveTrueAndServiceFalseAndOrganizationOrderByIdDesc(Organization organization);
    List<Sales> getSalesByActiveTrueAndServiceTrueOrderByIdDesc();
    List<Sales> getSalesByActiveTrueAndOrganizationOrderByIdDesc(Organization organization);
    List<Sales> getSalesByActiveTrueAndApproveTrueAndSaledFalseAndServiceFalse();
    List<Sales> getSalesByActiveTrueAndApproveTrueAndSaledTrueAndServiceFalseAndCreatedDateLessThanEqual(Date createdDate);
    Sales getSalesByIdAndActiveTrue(int salesId);
    Sales getSalesById(int salesId);
    List<Sales> getSalesByActiveTrueAndApproveTrueAndPayment_CashAndPayment_PeriodAndSaledFalseAndReturnedFalse(Boolean cash, Dictionary paymentPeriod);
    List<Sales> getSalesByActiveTrueAndApproveTrueAndPayment_CashAndPayment_PeriodAndSaledFalseAndSaleDateGreaterThanEqual(Boolean cash, Dictionary paymentPeriod, Date saleDate);
    List<Sales> getSalesByActiveTrueAndApproveTrueAndPayment_CashAndPayment_PeriodAndSaledFalseAndOrganizationAndReturnedFalse(Boolean cash, Dictionary paymentPeriod, Organization organization);
    List<Sales> getSalesByActiveTrueAndApproveTrueAndPayment_CashAndPayment_PeriodAndSaledFalseAndSaleDateGreaterThanEqualAndOrganization(Boolean cash, Dictionary paymentPeriod, Date saleDate, Organization organization);
    List<Sales> getSalesByActiveTrueAndApproveTrueAndPayment_CashAndSaledFalseAndReturnedFalseAndOrganization_Id(Boolean cash, Integer organizationId);
    List<Sales> getSalesByActiveTrueAndApproveTrueAndPayment_CashAndSaledFalseAndReturnedFalse(Boolean cash);
    List<Sales> getSalesByActiveTrueAndApproveTrueAndPayment_CashAndSaleDateAndSaledFalseAndReturnedFalse(Boolean cash, Date saleDate);
    List<Sales> getSalesByActiveTrueAndApproveTrueAndPayment_CashAndSaleDateAndSaledFalseAndOrganizationAndReturnedFalse(Boolean cash, Date saleDate, Organization organization);
    List<Sales> getSalesByActiveTrueAndApproveTrueAndServiceFalseAndCustomerAndOrganizationAndReturnedFalseOrderByIdDesc(Customer customer, Organization organization);
    List<Sales> getSalesByActiveTrueAndApproveTrueAndSaledFalseAndIdNotInOrderByApproveDateAsc(List<Integer> ids);
    List<Sales> getSalesByActiveTrueAndApproveTrueAndSaledFalseAndTaxConfigurationAndReturnedFalse(TaxConfiguration taxConfiguration);
    List<Sales> getSalesByActiveTrueAndApproveTrueAndSaledFalseAndOrganization(Organization organization);
    List<Sales> getSalesByActiveTrueAndReturnedFalseAndApproveDateBetweenAndCanavasser(Date start, Date end, Employee employee);
    List<Sales> getSalesByActiveTrueAndReturnedFalseAndApproveDateBetweenAndConsole(Date start, Date end, Employee employee);
    List<Sales> getSalesByActiveTrueAndReturnedFalseAndApproveDateBetweenAndVanLeader(Date start, Date end, Employee employee);
    List<Sales> getSalesByActiveTrueAndReturnedFalseAndApproveDateBetweenAndDealer(Date start, Date end, Employee employee);
}