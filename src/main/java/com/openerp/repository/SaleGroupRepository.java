package com.openerp.repository;

import com.openerp.entity.Contact;
import com.openerp.entity.SaleGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleGroupRepository extends JpaRepository<SaleGroup, Integer> {
}