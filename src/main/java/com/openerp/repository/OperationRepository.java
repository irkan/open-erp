package com.openerp.repository;

import com.openerp.entity.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OperationRepository extends JpaRepository<Operation, Integer> {
    Operation getOperationById(int id);
    List<Operation> getOperationsByActiveTrue();
}