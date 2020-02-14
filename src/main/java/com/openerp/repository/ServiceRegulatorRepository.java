package com.openerp.repository;

import com.openerp.entity.Dictionary;
import com.openerp.entity.Sales;
import com.openerp.entity.ServiceRegulator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ServiceRegulatorRepository extends JpaRepository<ServiceRegulator, Integer>, JpaSpecificationExecutor<ServiceRegulator> {
    List<ServiceRegulator> getServiceRegulatorsBySales(Sales sales);
    List<ServiceRegulator> getServiceRegulatorsBySalesAndServiceNotification_Id(Sales sales, Integer serviceNotificationId);
}