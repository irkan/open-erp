package com.openerp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hr_employee_id")
    private Employee employee;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "payroll_working_hour_record_employee_id")
    private WorkingHourRecordEmployee workingHourRecordEmployee;

    @Column(name = "is_approve", nullable = false, columnDefinition="boolean default false")
    private Boolean approve = false;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "approve_date")
    private Date approveDate;

    @OneToMany(mappedBy = "salaryEmployee", cascade = CascadeType.ALL)
    private List<SalaryEmployeeDetail> salaryEmployeeDetails;

    public SalaryEmployee(Salary salary, Employee employee, WorkingHourRecordEmployee workingHourRecordEmployee) {
        this.salary = salary;
        this.employee = employee;
        this.workingHourRecordEmployee = workingHourRecordEmployee;
    }
}
