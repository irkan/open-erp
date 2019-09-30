package com.openerp.repository;

import com.openerp.entity.Salary;
import com.openerp.entity.Vacation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaryRepository extends JpaRepository<Salary, Integer> {
}