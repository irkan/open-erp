package com.openerp.repository;

import com.openerp.entity.Migration;
import com.openerp.entity.MigrationDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MigrationDetailRepository extends JpaRepository<MigrationDetail, Integer> {
    List<MigrationDetail> getMigrationDetailsByActiveTrue();
    List<MigrationDetail> getMigrationDetailsByMigrationId(Integer migrationId);
    List<MigrationDetail> getMigrationDetailsByActiveTrueAndMigrationId(Integer migrationId);
}