package com.openerp.repository;

import com.openerp.entity.ServiceRegulatorTask;
import com.openerp.entity.ServiceTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRegulatorTaskRepository extends JpaRepository<ServiceRegulatorTask, Integer> {

}