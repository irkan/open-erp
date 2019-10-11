package com.openerp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "payroll_salary_employee")
@Getter
@Setter
@NoArgsConstructor
public class SalaryEmployee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "payroll_sequence")
    @SequenceGenerator(sequenceName = "aa_payroll_sequence", allocationSize = 1, name = "payroll_sequence")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "payroll_salary_id")
    private Salary salary;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "payroll_working_hour_record_employee_id")
    private WorkingHourRecordEmployee workingHourRecordEmployee;

    public SalaryEmployee(Salary salary, WorkingHourRecordEmployee workingHourRecordEmployee) {
        this.salary = salary;
        this.workingHourRecordEmployee = workingHourRecordEmployee;
    }
}
