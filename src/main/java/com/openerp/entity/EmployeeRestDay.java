package com.openerp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "hr_employee_rest_day")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class EmployeeRestDay {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "hr_sequence")
    @SequenceGenerator(sequenceName = "aa_hr_sequence", allocationSize = 1, name = "hr_sequence")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @ToString.Exclude
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hr_employee_id")
    private Employee employee;

    @Column(name = "description")
    private String description;

    @Column(name = "identifier")
    private String identifier;

    @Column(name = "day")
    private int day;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "admin_dictionary_week_day_id", nullable = false)
    private Dictionary type;

    public EmployeeRestDay(Employee employee, String description, String identifier, int day, Dictionary type) {
        this.employee = employee;
        this.description = description;
        this.identifier = identifier;
        this.day = day;
        this.type = type;
    }
}
