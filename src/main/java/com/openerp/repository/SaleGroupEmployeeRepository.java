package com.openerp.repository;

import com.openerp.entity.SaleGroup;
import com.openerp.entity.SaleGroupEmployee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleGroupEmployeeRepository extends JpaRepository<SaleGroupEmployee, Integer> {
}