package com.openerp.repository;

import com.openerp.entity.UserModuleOperation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserModuleOperationRepository extends JpaRepository<UserModuleOperation, Integer> {
    List<UserModuleOperation> findAllByUser_Id(int userId);
}