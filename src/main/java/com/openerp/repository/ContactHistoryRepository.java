package com.openerp.repository;

import com.openerp.entity.ContactHistory;
import com.openerp.entity.Organization;
import com.openerp.entity.Sales;
import com.openerp.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Date;
import java.util.List;

public interface ContactHistoryRepository extends JpaRepository<ContactHistory, Integer>, JpaSpecificationExecutor<ContactHistory> {
    ContactHistory getContactHistoryById(int id);
    List<ContactHistory> getContactHistoriesByActiveTrue();
    List<ContactHistory> getContactHistoriesByActiveTrueAndSales_Id(int salesId);
    List<ContactHistory> getContactHistoriesByActiveTrueAndSalesOrderByIdDesc(Sales sales);
    List<ContactHistory> getContactHistoriesByActiveTrueAndNextContactDateAndSalesApproveAndSalesSaledAndOrganizationAndNextContactDateIsNotNullOrderByIdDesc(Date nextContactDate, Boolean approve, Boolean saled, Organization organization);
    List<ContactHistory> getContactHistoriesByActiveTrueAndNextContactDateAndSalesApproveAndOrganizationOrderByIdDesc(Date nextContactDate, Boolean approve, Organization organization);
    List<ContactHistory> getContactHistoriesByActiveTrueAndNextContactDateOrderByIdDesc(Date nextContactDate);
    List<ContactHistory> getContactHistoriesByActiveTrueAndNextContactDateAndSalesApproveAndSalesSaledAndNextContactDateIsNotNullOrderByIdDesc(Date nextContactDate, Boolean approve, Boolean saled);
    List<ContactHistory> getContactHistoriesByActiveTrueAndNextContactDateAndSalesApproveOrderByIdDesc(Date nextContactDate, Boolean approve);
}