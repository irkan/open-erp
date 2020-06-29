package com.openerp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "employee_rest_day")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class EmployeeRestDay {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "employee_rest_day_seq")
    @SequenceGenerator(sequenceName = "employee_rest_day_seq", allocationSize = 1, name = "employee_rest_day_seq")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @ToString.Exclude
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(name = "description")
    private String description;

    @Column(name = "identifier")
    private String identifier;

    @Column(name = "day")
    private int day;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dictionary_week_day_id", nullable = false)
    private Dictionary type;

    public EmployeeRestDay(Employee employee, String description, String identifier, int day, Dictionary type) {
        this.employee = employee;
        this.description = description;
        this.identifier = identifier;
        this.day = day;
        this.type = type;
    }
}
