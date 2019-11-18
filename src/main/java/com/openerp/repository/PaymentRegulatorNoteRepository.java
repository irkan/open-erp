package com.openerp.repository;

import com.openerp.entity.Payment;
import com.openerp.entity.PaymentRegulatorNote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRegulatorNoteRepository extends JpaRepository<PaymentRegulatorNote, Integer> {
    PaymentRegulatorNote getPaymentRegulatorNoteById(int id);
    List<PaymentRegulatorNote> getPaymentRegulatorNotesByActiveTrue();
    List<PaymentRegulatorNote> getPaymentRegulatorNotesByActiveTrueAndPayment_Id(int paymentId);
}