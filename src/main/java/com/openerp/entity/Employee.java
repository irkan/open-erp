package com.openerp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "hr_employee")
@Getter
@Setter
@NoArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "hr_sequence")
    @SequenceGenerator(sequenceName = "aa_hr_sequence", allocationSize = 1, name = "hr_sequence")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hr_person_id")
    private Person person;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "admin_dictionary_position_id", nullable = false)
    private Dictionary position;

    @Temporal(TemporalType.DATE)
    @Column(name = "contract_start_date")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date contractStartDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "contract_end_date")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date contractEndDate;

    @Column(name = "salary")
    private Double salary;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hr_organization_id", nullable = false)
    private Organization organization;

    @JsonIgnore
    @OneToOne(mappedBy = "employee")
    private User user;

    public Employee(Person person, Dictionary position, Date contractStartDate, Date contractEndDate, Double salary, Organization organization) {
        this.person = person;
        this.position = position;
        this.contractStartDate = contractStartDate;
        this.contractEndDate = contractEndDate;
        this.salary = salary;
        this.organization = organization;
    }
}
