package com.openerp.task;

import com.openerp.controller.SkeletonController;
import com.openerp.entity.CurrencyRate;
import com.openerp.entity.Log;
import com.openerp.repository.CurrencyRateRepository;
import com.openerp.repository.LogRepository;
import com.openerp.util.Util;
import com.openerp.util.UtilJson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CbarCurrencyRateTask {
    private static final Logger log = Logger.getLogger(CbarCurrencyRateTask.class);

    @Value("${cbar.currencies.endpoint}")
    String cbarCurrenciesEndpoint;

    @Autowired
    SkeletonController skeletonController;

    @Autowired
    CurrencyRateRepository currencyRateRepository;

    @Autowired
    LogRepository logRepository;

    @Scheduled(fixedDelay = 14400000)
    public void task() {
        try{
            log.info("Cbar Currency Rate Task Start");
            currencyRateRepository.deleteAll();
            List<CurrencyRate> currencyRates = Util.getCurrenciesRate(cbarCurrenciesEndpoint);
            currencyRateRepository.saveAll(currencyRates);
            Log logObject = new Log("currency_rate", "reload", null, "", "", "CbarCurrencyRateTask ilə məzənnə yeniləndi", UtilJson.toJson(currencyRates));
            logRepository.save(logObject);
            log.info("Cbar Currency Rate Task End");
        } catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
    }
}
