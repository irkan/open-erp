package com.openerp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "salary_employee_detail")
@Getter
@Setter
@NoArgsConstructor
public class SalaryEmployeeDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "salary_employee_detail_seq")
    @SequenceGenerator(sequenceName = "salary_employee_detail_seq", allocationSize = 1, name = "salary_employee_detail_seq")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "salary_employee_id")
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
