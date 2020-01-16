package com.openerp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import java.util.Date;

@Entity
@Table(name = "sale_schedule")
@Getter
@Setter
@NoArgsConstructor
public class Schedule {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "sale_sequence")
    @SequenceGenerator(sequenceName = "aa_sale_sequence", allocationSize = 1, name = "sale_sequence")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hr_organization_id", nullable = false)
    private Organization organization;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sale_payment_id")
    private Payment payment;

    @DecimalMin(value = "0", message = "Minimum 0 olmalıdır")
    @Column(name = "amount", nullable = false, columnDefinition="Decimal(10,2) default 0")
    private Double amount=0d;

    @Transient
    private Double amountFrom;

    @Temporal(TemporalType.DATE)
    @Column(name = "schedule_date")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date scheduleDate;

    @Transient
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date scheduleDateFrom;

    @DecimalMin(value = "0", message = "Minimum 0 olmalıdır")
    @Column(name = "payable_amount", nullable = false, columnDefinition="Decimal(10,2) default 0")
    private Double payableAmount=0d;

    @Column(name = "is_active", nullable = false, columnDefinition="boolean default true")
    private Boolean active = true;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false)
    private Date createdDate = new Date();

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "created_by_admin_user_id")
    private User createdUser;

    public Schedule(Payment payment, @DecimalMin(value = "0", message = "Minimum 0 olmalıdır") Double amount, Date scheduleDate) {
        this.payment = payment;
        this.amount = amount;
        this.scheduleDate = scheduleDate;
    }

    public Schedule(Payment payment, Organization organization) {
        this.payment = payment;
        this.organization = organization;
    }

    public Schedule(Payment payment, Organization organization, Double amount) {
        this.payment = payment;
        this.organization = organization;
        this.amount = amount;
    }
}
