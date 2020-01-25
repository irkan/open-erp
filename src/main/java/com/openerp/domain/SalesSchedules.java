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
public class SalesSchedules {
    private List<Schedule> schedules;
    private Sales sales;

    public SalesSchedules(List<Schedule> schedules, Sales sales) {
        this.schedules = schedules;
        this.sales = sales;
    }
}
