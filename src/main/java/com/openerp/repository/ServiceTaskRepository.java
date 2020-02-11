package com.openerp.repository;

import com.openerp.entity.Sales;
import com.openerp.entity.ServiceTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ServiceTaskRepository extends JpaRepository<ServiceTask, Integer>, JpaSpecificationExecutor<ServiceTask> {
    ServiceTask getServiceTaskById(int id);
}