package com.openerp.repository;

import com.openerp.entity.MigrationDetail;
import com.openerp.entity.MigrationDetailServiceRegulator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MigrationDetailServiceRegulatorRepository extends JpaRepository<MigrationDetailServiceRegulator, Integer> {
    List<MigrationDetailServiceRegulator> getMigrationDetailServiceRegulatorsByMigrationId(Integer migrationId);
}