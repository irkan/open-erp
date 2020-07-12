package com.openerp.task;

import com.openerp.entity.*;
import com.openerp.repository.*;
import com.openerp.util.DateUtility;
import com.openerp.util.Util;
import com.openerp.util.UtilJson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class SaledStatusTask {
    private static final Logger log = Logger.getLogger(SaledStatusTask.class);

    @Autowired
    SalesRepository salesRepository;

    @Autowired
    SalesInventoryRepository salesInventoryRepository;

    @Autowired
    ContactHistoryRepository contactHistoryRepository;

    @Autowired
    LogRepository logRepository;

    @Scheduled(cron="0 0 2 * * *") //hergun seher saat 2 de iwliyecek
    public void saled() {
        try{
            log.info("Saled Status (For Not Saled) Task Start");
            for(Sales sale: salesRepository.getSalesByActiveTrueAndApproveTrueAndSaledFalseAndServiceFalse()){
                try{
                    if(sale.getPayment()!=null){
                        if(sale.getPayment().getLastPrice()<=Util.calculateInvoice(sale.getInvoices())){
                            sale.setSaled(true);
                            salesRepository.save(sale);
                            sale.setSalesInventories(salesInventoryRepository.getSalesInventoriesByActiveTrueAndSales_Id(sale.getId()));
                            sale.setContactHistories(contactHistoryRepository.getContactHistoriesByActiveTrueAndSales_Id(sale.getId()));
                            Log logObject = new Log("sales", "create/edit", sale.getId(), "", "", "Satıldı statusu yeniləndi. "+sale.getSaled()+" oldu. (SaledStatusTask-NotSaled | Satıldı): " + sale.getId(), UtilJson.toJson(sale));
                            logRepository.save(logObject);
                            log.info("Satıldı statusu yeniləndi. "+sale.getSaled()+" oldu. (SaledStatusTask-NotSaled | Satıldı): " + sale.getId());
                        }
                    }
                } catch (Exception e){
                    log.error(e.getMessage(), e);
                }
            }
            log.info("Saled Status (For Not Saled) Task End");
        } catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
    }


    @Scheduled(cron="0 30 2 * * *") //hergun seher saat 2:30 da iwliyecek
    public void notSaled() {
        try{
            log.info("Saled Status (For Saled) Task Start");
            for(Sales sale: salesRepository.getSalesByActiveTrueAndApproveTrueAndSaledTrueAndServiceFalseAndCreatedDateLessThanEqual(DateUtility.addDay(-3))){
                try{
                    if(sale.getPayment()!=null){
                        if(sale.getPayment().getLastPrice()>Util.calculateInvoice(sale.getInvoices())){
                            sale.setSaled(false);
                            salesRepository.save(sale);
                            sale.setSalesInventories(salesInventoryRepository.getSalesInventoriesByActiveTrueAndSales_Id(sale.getId()));
                            sale.setContactHistories(contactHistoryRepository.getContactHistoriesByActiveTrueAndSales_Id(sale.getId()));
                            Log logObject = new Log("sales", "create/edit", sale.getId(), "", "", "Satıldı statusu yeniləndi. "+sale.getSaled()+" oldu. (SaledStatusTask-Saled | Aktivə qaytarıldı): " + sale.getId(), UtilJson.toJson(sale));
                            logRepository.save(logObject);
                            log.info("Satıldı statusu yeniləndi. "+sale.getSaled()+" oldu. (SaledStatusTask-Saled | Aktivə qaytarıldı): " + sale.getId());
                        }
                    }
                } catch (Exception e){
                    log.error(e.getMessage(), e);
                }
            }
            log.info("Saled Status (For Saled) Task End");
        } catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
    }
}
