package com.openerp.entity;

import com.github.javafaker.Bool;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Entity
@Table(name = "advance")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Advance {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "advance_seq")
    @SequenceGenerator(sequenceName = "advance_seq", allocationSize = 1, name = "advance_seq")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dictionary_advance_id")
    private Dictionary advance;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sales_id")
    private Sales sales;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @Column(name = "payed", columnDefinition="Decimal(10,2) default 0")
    private Double payed=0d;

    @ToString.Exclude
    @Transient
    private Double payedFrom;

    @Pattern(regexp=".{0,250}",message="Maksimum 250 simvol ola bilər")
    @Column(name = "formula")
    private String formula;

    @Pattern(regexp=".{0,250}",message="Maksimum 250 simvol ola bilər")
    @Column(name = "description")
    private String description;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @Column(name = "advance_date")
    private Date advanceDate=new Date();

    @ToString.Exclude
    @Transient
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date advanceDateFrom;

    @Column(name = "is_approve", nullable = false, columnDefinition="boolean default false")
    private Boolean approve = false;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @Column(name = "approve_date")
    private Date approveDate = new Date();

    @Column(name = "is_active", nullable = false, columnDefinition="boolean default true")
    private Boolean active = true;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false)
    private Date createdDate = new Date();

    public Advance(Dictionary advance, Employee employee, Organization organization, @Pattern(regexp = ".{0,250}", message = "Maksimum 50 simvol ola bilər") String description, @Pattern(regexp = ".{0,250}", message = "Maksimum 50 simvol ola bilər") String formula, Date advanceDate, double payed) {
        this.advance = advance;
        this.employee = employee;
        this.organization = organization;
        this.description = description;
        this.formula = formula;
        this.advanceDate = advanceDate;
        this.payed = payed;
    }

    public Advance(Organization organization) {
        this.organization = organization;;
    }

    public Advance(Organization organization, Double payed, Date advanceDate) {
        this.organization = organization;
        this.payed = payed;
        this.advanceDate = advanceDate;
    }

    public Advance(Organization organization, Double payed, Date advanceDate, Boolean approve) {
        this.organization = organization;
        this.payed = payed;
        this.advanceDate = advanceDate;
        this.approve = approve;
    }
}
