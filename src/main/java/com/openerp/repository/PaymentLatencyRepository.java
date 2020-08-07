package com.openerp.repository;

import com.openerp.entity.Payment;
import com.openerp.entity.PaymentLatency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentLatencyRepository extends JpaRepository<PaymentLatency, Integer> {
}