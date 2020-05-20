package com.openerp.repository;

import com.openerp.entity.Migration;
import com.openerp.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MigrationRepository extends JpaRepository<Migration, Integer> {
    List<Migration> getMigrationsByActiveTrue();
    List<Migration> getMigrationsByActiveTrueAndStatusOrderByUploadDate(Integer status);
    Migration getMigrationById(Integer id);
}