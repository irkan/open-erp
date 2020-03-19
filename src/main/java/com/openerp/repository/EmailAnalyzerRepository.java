package com.openerp.repository;

import com.openerp.entity.EmailAnalyzer;
import com.openerp.entity.Financing;
import com.openerp.entity.Inventory;
import com.openerp.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface EmailAnalyzerRepository extends JpaRepository<EmailAnalyzer, Integer>, JpaSpecificationExecutor<EmailAnalyzer> {
}