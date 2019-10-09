package com.openerp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Entity
@Table(name = "hr_employee_detail")
@Getter
@Setter
@NoArgsConstructor
public class EmployeeDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "hr_sequence")
    @SequenceGenerator(sequenceName = "aa_hr_sequence", allocationSize = 1, name = "hr_sequence")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hr_employee_id")
    private Employee employee;

    @Column(name = "key_field")
    private String key;

    @Column(name = "value")
    private String value;

    public EmployeeDetail(Employee employee, String key, String value) {
        this.employee = employee;
        this.key = key;
        this.value = value;
    }
}
