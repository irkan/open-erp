package com.openerp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.openerp.entity.Organization;
import com.openerp.entity.Payment;
import com.openerp.entity.Sales;
import com.openerp.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class Schedule {
    private Double amount;

    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date scheduleDate;

    private Double payableAmount=0d;

    private Sales sales;

    public Schedule(Double amount, Date scheduleDate) {
        this.amount = amount;
        this.scheduleDate = scheduleDate;
    }
}
