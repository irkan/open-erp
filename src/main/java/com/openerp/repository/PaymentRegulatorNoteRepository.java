package com.openerp.repository;

import com.openerp.entity.ContactHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRegulatorNoteRepository extends JpaRepository<ContactHistory, Integer> {
    ContactHistory getPaymentRegulatorNoteById(int id);
    List<ContactHistory> getPaymentRegulatorNotesByActiveTrue();
    List<ContactHistory> getPaymentRegulatorNotesByActiveTrueAndPayment_Id(int paymentId);
}