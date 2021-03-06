package com.openerp.repository;

import com.openerp.entity.Employee;
import com.openerp.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface LogRepository extends JpaRepository<Log, Integer>, JpaSpecificationExecutor<Log> {
    List<Log> getLogsByActiveTrueOrderByOperationDateDesc();
    List<Log> getLogsByActiveTrueAndTableNameAndRowIdAndOperationOrderByIdDesc(String tableName, Integer rowId, String operation);
    Log getLogById(int id);
}