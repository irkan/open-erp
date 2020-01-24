package com.openerp.repository;

import com.openerp.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    Payment getPaymentById(Integer id);
}