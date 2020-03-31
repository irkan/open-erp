package com.openerp.repository;

import com.openerp.entity.ContactHistory;
import com.openerp.entity.Sales;
import com.openerp.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ContactHistoryRepository extends JpaRepository<ContactHistory, Integer>, JpaSpecificationExecutor<ContactHistory> {
    ContactHistory getContactHistoryById(int id);
    List<ContactHistory> getContactHistoriesByActiveTrue();
    List<ContactHistory> getContactHistoriesByActiveTrueAndSales_Id(int salesId);
    List<ContactHistory> getContactHistoriesByActiveTrueAndSalesOrderByIdDesc(Sales sales);
}