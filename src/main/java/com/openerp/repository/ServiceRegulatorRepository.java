package com.openerp.repository;

import com.openerp.entity.Sales;
import com.openerp.entity.ServiceRegulator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceRegulatorRepository extends JpaRepository<ServiceRegulator, Integer> {
    List<ServiceRegulator> getServiceRegulatorsBySales(Sales sales);
}