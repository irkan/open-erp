package com.openerp.repository;

import com.openerp.entity.Demonstration;
import com.openerp.entity.Organization;
import com.openerp.entity.Sales;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DemonstrationRepository extends JpaRepository<Demonstration, Integer> {
}