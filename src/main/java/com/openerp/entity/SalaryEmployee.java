package com.openerp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "salary_employee")
@Getter
@Setter
@NoArgsConstructor
public class SalaryEmployee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "salary_employee_seq")
    @SequenceGenerator(sequenceName = "salary_employee_seq", allocationSize = 1, name = "salary_employee_seq")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "salary_id")
    private Salary salary;

    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "working_hour_record_employee_id")
    private WorkingHourRecordEmployee workingHourRecordEmployee;

    @OneToMany(mappedBy = "salaryEmployee", cascade = CascadeType.ALL)
    private List<SalaryEmployeeDetail> salaryEmployeeDetails;

    public SalaryEmployee(Salary salary, Employee employee, WorkingHourRecordEmployee workingHourRecordEmployee) {
        this.salary = salary;
        this.employee = employee;
        this.workingHourRecordEmployee = workingHourRecordEmployee;
    }
}
