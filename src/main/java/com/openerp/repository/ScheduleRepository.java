package com.openerp.repository;

import com.openerp.entity.Organization;
import com.openerp.entity.Payment;
import com.openerp.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    @Query(value = "from Schedule t where t.invoicing='false' and t.amount>t.payableAmount and t.scheduleDate<:current order by t.id desc")
    List<Schedule> getSchedules(@Param("current") Date current);

    @Query(value = "from Schedule t inner join t.payment p inner join p.sales s where t.invoicing='false' and t.amount>t.payableAmount and t.scheduleDate<:current and s.organization=:organization order by t.id desc")
    List<Schedule> getSchedulesByOrganization(@Param("current") Date current, @Param("organization") Organization organization);

    @Query(value = "from Schedule t where t.invoicing='false' and t.amount>t.payableAmount and t.scheduleDate<:current and t.payment.id=:paymentId order by t.id")
    List<Schedule> getScheduleDetails(@Param("current") Date current, @Param("paymentId") Integer paymentId);

    List<Schedule> getSchedulesByPayment_IdAndPaymentActiveOrderByScheduleDateAsc(Integer paymentId, Boolean paymentActive);

}