package com.openerp.task;

import com.openerp.controller.SkeletonController;
import com.openerp.entity.Sales;
import com.openerp.entity.ServiceRegulator;
import com.openerp.repository.SalesRepository;
import com.openerp.util.Util;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SaledStatusTask {
    private static final Logger log = Logger.getLogger(SaledStatusTask.class);

    @Autowired
    SalesRepository salesRepository;

    @Autowired
    SkeletonController skeletonController;

    @Scheduled(fixedDelay = 600000)
    public void saledStatus() {
        try{
            log.info("Saled Status Task Start");
            for(Sales sales: salesRepository.getSalesByActiveTrueAndServiceFalseOrderByIdDesc()){
                try {
                    if(sales.getPayment().getLastPrice()<= Util.calculateInvoice(sales.getInvoices())){
                        sales.setSaled(true);
                        salesRepository.save(sales);
                        skeletonController.log("sale_sales", "create/edit", sales.getId(), sales.toString());
                    }
                } catch (Exception e){
                    e.printStackTrace();
                    log.error(e);
                }
            }
            log.info("Saled Status Task End");
        } catch (Exception e){
            log.error(e);
        }
    }
}
