package com.openerp.repository;

import com.openerp.entity.Organization;
import com.openerp.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    Transaction getTransactionById(int id);
    List<Transaction> getTransactionsByOrderByApproveDescCreatedDateDesc();
    List<Transaction> getTransactionsByOrganizationOrderByApproveDescCreatedDateDesc(Organization organization);
}