package com.openerp.task;

import com.openerp.controller.SkeletonController;
import com.openerp.repository.CurrencyRateRepository;
import com.openerp.util.Util;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CbarCurrencyRateTask {
    private static final Logger log = Logger.getLogger(CbarCurrencyRateTask.class);

    @Value("${cbar.currencies.endpoint}")
    String cbarCurrenciesEndpoint;

    @Autowired
    SkeletonController skeletonController;

    @Autowired
    CurrencyRateRepository currencyRateRepository;

    @Scheduled(fixedDelay = 14400000)
    public void task() {
        try{
            log.info("Cbar Currency Rate Task Start");
            currencyRateRepository.deleteAll();
            currencyRateRepository.saveAll(Util.getCurrenciesRate(cbarCurrenciesEndpoint));
            skeletonController.log("admin_currency_rate", "reload", null, null, "CbarCurrencyRateTask ilə məzənnə yeniləndi");
            log.info("Cbar Currency Rate Task End");
        } catch (Exception e){
            log.error(e);
        }
    }
}
