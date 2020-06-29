package com.openerp.repository;

import com.openerp.entity.MigrationEmployee;
import com.openerp.entity.MigrationUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MigrationUserRepository extends JpaRepository<MigrationUser, Integer> {
}