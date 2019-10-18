package com.openerp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

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

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
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

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hr_organization_id", nullable = false)
    private Organization organization;

    @Column(name = "social_card_number")
    private String socialCardNumber;

    @Column(name = "bank_account_number")
    private String bankAccountNumber;

    @Column(name = "bank_card_number")
    private String bankCardNumber;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EmployeeDetail> employeeDetails;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EmployeeRestDay> employeeRestDays;

    @JsonIgnore
    @OneToOne(mappedBy = "employee")
    private User user;

    public Employee(Person person, Dictionary position, Date contractStartDate, Date contractEndDate, Organization organization) {
        this.person = person;
        this.position = position;
        this.contractStartDate = contractStartDate;
        this.contractEndDate = contractEndDate;
        this.organization = organization;
    }
}
