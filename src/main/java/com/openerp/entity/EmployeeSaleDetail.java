package com.openerp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "employee_sale_detail")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class EmployeeSaleDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "employee_sale_detail_seq")
    @SequenceGenerator(sequenceName = "employee_sale_detail_seq", allocationSize = 1, name = "employee_sale_detail_seq")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @ToString.Exclude
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dictionary_employee_sale_field_id")
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
