package com.openerp.repository;

import com.openerp.entity.ModuleOperation;
import com.openerp.entity.UserModuleOperation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserModuleOperationRepository extends JpaRepository<UserModuleOperation, Integer> {
    List<UserModuleOperation> getUserModuleOperationsByUser_IdAndUser_Active(int userId, boolean active);
    List<UserModuleOperation> getUserModuleOperationsByModuleOperation_Id(int id);
}