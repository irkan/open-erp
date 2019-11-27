package com.openerp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Entity
@Table(name = "payroll_advance")
@Getter
@Setter
@NoArgsConstructor
public class Advance {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "payroll_sequence")
    @SequenceGenerator(sequenceName = "aa_payroll_sequence", allocationSize = 1, name = "payroll_sequence")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "admin_dictionary_advance_id", nullable = false)
    private Dictionary advance;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hr_employee_id", nullable = false)
    private Employee employee;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hr_organization_id")
    private Organization organization;

    @DecimalMin(value = "0", message = "Minimum 0 olmalıdır")
    @Column(name = "payed", columnDefinition="Decimal(10,2) default 0")
    private double payed=0d;

    @Pattern(regexp=".{0,250}",message="Maksimum 250 simvol ola bilər")
    @Column(name = "formula")
    private String formula;

    @Pattern(regexp=".{0,250}",message="Maksimum 250 simvol ola bilər")
    @Column(name = "description")
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "advance_date")
    private Date advanceDate=new Date();

    @Column(name = "is_approve", nullable = false, columnDefinition="boolean default false")
    private Boolean approve = false;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "approve_date")
    private Date approveDate;

    @Column(name = "is_debt", nullable = false, columnDefinition="boolean default true")
    private Boolean debt = false;

    @Column(name = "is_active", nullable = false, columnDefinition="boolean default true")
    private Boolean active = true;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false)
    private Date createdDate = new Date();

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "created_by_admin_user_id")
    private User createdUser;

    public Advance(Dictionary advance, Employee employee, Organization organization, @Pattern(regexp = ".{0,250}", message = "Maksimum 50 simvol ola bilər") String description, @Pattern(regexp = ".{0,250}", message = "Maksimum 50 simvol ola bilər") String formula, Date advanceDate, double payed) {
        this.advance = advance;
        this.employee = employee;
        this.organization = organization;
        this.description = description;
        this.formula = formula;
        this.advanceDate = advanceDate;
        this.payed = payed;
    }
}
