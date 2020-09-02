package com.openerp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "employee")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "employee_seq")
    @SequenceGenerator(sequenceName = "employee_seq", allocationSize = 1, initialValue = 100001, name = "employee_seq")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id")
    private Person person;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dictionary_position_id", nullable = false)
    private Dictionary position;

    @Temporal(TemporalType.DATE)
    @Column(name = "contract_start_date")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date contractStartDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "contract_end_date")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date contractEndDate;

    @Pattern(regexp=".{0,250}", message="Maksimum 250 simvol ola bilər")
    @Column(name = "leave_reason")
    private String leaveReason;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    @Column(name = "social_card_number")
    private String socialCardNumber;

    @Column(name = "bank_account_number")
    private String bankAccountNumber;

    @Column(name = "bank_card_number")
    private String bankCardNumber;

    @Column(name = "is_specialist_or_manager", nullable = false, columnDefinition="boolean default false")
    private Boolean specialistOrManager = false;

    @Pattern(regexp=".{0,250}", message="Maksimum 250 simvol ola bilər")
    @Column(name = "description")
    private String description;

    @Column(name = "is_active", nullable = false, columnDefinition="boolean default true")
    private Boolean active = true;

    @ToString.Exclude
    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
    private List<EmployeePayrollDetail> employeePayrollDetails;

    @ToString.Exclude
    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
    private List<EmployeeSaleDetail> employeeSaleDetails;

    @ToString.Exclude
    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
    private List<EmployeeRestDay> employeeRestDays;

    @ToString.Exclude
    @JsonIgnore
    @OneToOne(mappedBy = "employee")
    private User user;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false)
    private Date createdDate = new Date();

    public Employee(Person person, Dictionary position, Date contractStartDate, Date contractEndDate, Organization organization) {
        this.person = person;
        this.position = position;
        this.contractStartDate = contractStartDate;
        this.contractEndDate = contractEndDate;
        this.organization = organization;
    }

    public Employee(Person person, Dictionary position, Date contractStartDate, Date contractEndDate, Organization organization, String socialCardNumber, String bankAccountNumber, String bankCardNumber, Boolean specialistOrManager, @Pattern(regexp = ".{0,250}", message = "Maksimum 250 simvol ola bilər") String description) {
        this.person = person;
        this.position = position;
        this.contractStartDate = contractStartDate;
        this.contractEndDate = contractEndDate;
        this.organization = organization;
        this.socialCardNumber = socialCardNumber;
        this.bankAccountNumber = bankAccountNumber;
        this.bankCardNumber = bankCardNumber;
        this.specialistOrManager = specialistOrManager;
        this.description = description;
    }

    public Employee(Organization organization) {
        this.organization = organization;
    }
}
