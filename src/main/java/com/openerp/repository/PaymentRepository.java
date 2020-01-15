package com.openerp.repository;

import com.openerp.entity.Payment;
import com.openerp.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    Payment getPaymentById(Integer id);
}