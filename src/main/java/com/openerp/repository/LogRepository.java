package com.openerp.repository;

import com.openerp.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogRepository extends JpaRepository<Log, Integer> {
    List<Log> getLogsByActiveTrueOrderByOperationDateDesc();
    Log getLogById(int id);
}