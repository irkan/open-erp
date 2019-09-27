package com.openerp.repository;

import com.openerp.entity.Action;
import com.openerp.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Integer> {
}