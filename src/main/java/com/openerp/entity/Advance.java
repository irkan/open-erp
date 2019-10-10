package com.openerp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "payroll_advance")
@Getter
@Setter
@NoArgsConstructor
public class Advance {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "hr_sequence")
    @SequenceGenerator(sequenceName = "aa_hr_sequence", allocationSize = 1, name = "hr_sequence")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "admin_dictionary_identifier_id", nullable = false)
    private Dictionary identifier;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hr_employee_id", nullable = false)
    private Employee employee;

    @Column(name = "payed", columnDefinition="Decimal(10,2) default 0")
    private double payed=0d;

    @Pattern(regexp=".{0,250}",message="Maksimum 50 simvol ola bilər")
    @Column(name = "description")
    private String description;

    @Temporal(TemporalType.DATE)
    @Column(name = "calculate_date")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date calculateDate;

    @Column(name = "is_approve", nullable = false, columnDefinition="boolean default true")
    private Boolean approve = true;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "approve_date")
    private Date approveDate;

    @Column(name = "is_active", nullable = false, columnDefinition="boolean default true")
    private Boolean active = true;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false)
    private Date createdDate = new Date();

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "created_by_admin_user_id")
    private User createdUser;

    public Advance(Dictionary identifier, Employee employee, @Pattern(regexp = ".{0,250}", message = "Maksimum 50 simvol ola bilər") String description, Date calculateDate, double payed, Boolean approve) {
        this.identifier = identifier;
        this.employee = employee;
        this.description = description;
        this.calculateDate = calculateDate;
        this.payed = payed;
        this.approve = approve;
    }
}
