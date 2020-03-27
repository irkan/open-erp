package com.openerp.repository;

import com.openerp.entity.Payment;
import com.openerp.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
/*    @Query(nativeQuery = true, value = "select p.*, sum(s.amount) amount, sum(s.payable_amount) payable_amount, " +
            "min(s.sale_payment_id) sale_payment_id, min(s.schedule_date) schedule_date " +
            "from sale_payment p left join sale_schedule s " +
            "on p.id=s.sale_payment_id where s.is_invoicing=0 and s.amount>s.payable_amount " +
            "and s.schedule_date<CURDATE() group by s.sale_payment_id order by p.priority desc")
    List<Payment> getPayments();

    @Query(value = "from Payment p left join Schedule s on p.id=s.payment where s.invoicing='false' and s.amount>s.payableAmount and s.scheduleDate<:current order by p.priority desc")
    List<Payment> getPayments2(@Param("current") Date current);*/
}