package com.openerp.repository;

import com.openerp.entity.Account;
import com.openerp.entity.Action;
import com.openerp.entity.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    List<Account> getAccountsByActiveTrue();
    List<Account> getAccountsByActiveTrueAndOrganization(Organization branch);
    Account getAccountById(int id);
    Account getAccountByAccountNumberAndActiveTrue(String accountNumber);
}