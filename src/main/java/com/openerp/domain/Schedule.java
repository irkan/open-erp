package com.openerp.domain;

import com.openerp.entity.Sales;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

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
