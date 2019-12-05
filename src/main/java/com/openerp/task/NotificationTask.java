package com.openerp.task;

import com.openerp.repository.CurrencyRateRepository;
import com.openerp.util.Util;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NotificationTask {
    private static final Logger log = Logger.getLogger(NotificationTask.class);

    @Value("${cbar.currencies.endpoint}")
    String cbarCurrenciesEndpoint;

    @Autowired
    CurrencyRateRepository currencyRateRepository;

    @Scheduled(fixedDelay = 300000)
    public void email() {
        try{
            log.info("Cbar Currency Rate Task Start");
            currencyRateRepository.deleteAll();
            currencyRateRepository.saveAll(Util.getCurrenciesRate(cbarCurrenciesEndpoint));
            log.info("Cbar Currency Rate Task End");
        } catch (Exception e){
            log.error(e);
        }
    }

    @Scheduled(fixedDelay = 600000)
    public void sms() {
        try{
            log.info("Cbar Currency Rate Task Start");
            currencyRateRepository.deleteAll();
            currencyRateRepository.saveAll(Util.getCurrenciesRate(cbarCurrenciesEndpoint));
            log.info("Cbar Currency Rate Task End");
        } catch (Exception e){
            log.error(e);
        }
    }
}
