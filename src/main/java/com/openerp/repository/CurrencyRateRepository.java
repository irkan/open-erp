package com.openerp.repository;

import com.openerp.entity.CurrencyRate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRateRepository extends JpaRepository<CurrencyRate, Integer> {
    CurrencyRate getCurrencyRateByCode(String code);
}