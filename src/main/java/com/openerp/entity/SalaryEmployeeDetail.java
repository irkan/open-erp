package com.openerp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "payroll_salary_employee_detail")
@Getter
@Setter
@NoArgsConstructor
public class SalaryEmployeeDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "payroll_sequence")
    @SequenceGenerator(sequenceName = "aa_payroll_sequence", allocationSize = 1, name = "payroll_sequence")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "payroll_salary_employee_id")
    private SalaryEmployee salaryEmployee;

    @Column(name = "description")
    private String description;

    @Column(name = "key_field")
    private String key;

    @Column(name = "value")
    private String value;

    @Column(name = "formula")
    private String formula;

    @Column(name = "skeleton_formula")
    private String skeletonFormula;

    public SalaryEmployeeDetail(SalaryEmployee salaryEmployee, String description, String key, String value, String formula, String skeletonFormula) {
        this.salaryEmployee = salaryEmployee;
        this.description = description;
        this.key = key;
        this.value = value;
        this.formula = formula;
        this.skeletonFormula = skeletonFormula;
    }
}
