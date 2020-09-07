package com.openerp.domain;

import com.openerp.entity.Dictionary;
import com.openerp.entity.Employee;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdvanceGroup {
    private Employee employee;
    private Report report;

    public AdvanceGroup(Employee employee,
                        Report report) {
        this.employee = employee;
        this.report = report;
    }
}
