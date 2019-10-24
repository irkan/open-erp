package com.openerp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "hr_employee_sale_detail")
@Getter
@Setter
@NoArgsConstructor
public class EmployeeSaleDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "hr_sequence")
    @SequenceGenerator(sequenceName = "aa_hr_sequence", allocationSize = 1, name = "hr_sequence")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hr_employee_id")
    private Employee employee;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "admin_dictionary_employee_sale_field_id")
    private Dictionary employeeSaleField;

    @Column(name = "key_field")
    private String key;

    @Column(name = "value")
    private String value;

    public EmployeeSaleDetail(Employee employee, Dictionary employeeSaleField, String key, String value) {
        this.employee = employee;
        this.employeeSaleField = employeeSaleField;
        this.key = key;
        this.value = value;
    }
}
