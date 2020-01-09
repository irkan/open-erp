package com.openerp.repository;

import com.openerp.entity.Financing;
import com.openerp.entity.Inventory;
import com.openerp.entity.Organization;
import com.openerp.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer>, JpaSpecificationExecutor<Transaction> {
    Transaction getTransactionById(int id);
    List<Transaction> getTransactionsByOrderByApproveDescCreatedDateDesc();
    List<Transaction> getTransactionsByOrganizationOrderByApproveDescCreatedDateDesc(Organization organization);
    List<Transaction> getTransactionsByInventoryAndApproveFalseAndOrganization(Inventory inventory, Organization organization);
}