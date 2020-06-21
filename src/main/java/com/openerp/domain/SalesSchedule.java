package com.openerp.domain;

import com.openerp.entity.Sales;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SalesSchedule {

    private List<Schedule> schedules;

    private Sales sales;

    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date scheduleDate=new Date();

    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date scheduleDateFrom=new Date();

    public SalesSchedule(List<Schedule> schedules, Sales sales) {
        this.schedules = schedules;
        this.sales = sales;
    }

    public SalesSchedule(Sales sales) {
        this.sales = sales;
    }

    public SalesSchedule(Sales sales, Date scheduleDate) {
        this.sales = sales;
        this.scheduleDate = scheduleDate;
    }
}
