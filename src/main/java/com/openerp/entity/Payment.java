package com.openerp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "payment")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Payment {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "payment_seq")
    @SequenceGenerator(sequenceName = "payment_seq", allocationSize = 1, name = "payment_seq")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @DecimalMin(value = "0", message = "Minimum 0 olmalıdır")
    @Column(name = "price", nullable = false, columnDefinition="Decimal(10,2) default 0")
    private Double price=0d;

    @DecimalMin(value = "0", message = "Minimum 0 olmalıdır")
    @Column(name = "last_price", nullable = false, columnDefinition="Decimal(10,2) default 0")
    private Double lastPrice;

    @ToString.Exclude
    @Transient
    private Double lastPriceFrom;

    @DecimalMin(value = "0", message = "Minimum 0 olmalıdır")
    @Column(name = "down", nullable = false, columnDefinition="Decimal(10,2) default 0")
    private Double down=0d;

    @DecimalMin(value = "0", message = "Minimum 0 olmalıdır")
    @Column(name = "schedule_price", columnDefinition="Decimal(10,2) default 0")
    private Double schedulePrice;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dictionary_payment_schedule_id")
    private Dictionary schedule;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dictionary_payment_period_id")
    private Dictionary period;

    @Pattern(regexp=".{0,10}", message="Maksimum 10 simvol ola bilər")
    @Column(name = "discount")
    private String discount;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "is_active", nullable = false, columnDefinition="boolean default true")
    private Boolean active = true;

    @Column(name = "is_cash", nullable = false, columnDefinition="boolean default false")
    private Boolean cash = false;

    @Column(name = "grace_period")
    private Integer gracePeriod = 0;

    @Transient
    private Boolean hasDiscount = false;

    @Transient
    private Integer latency;

    @Transient
    private Double sumOfInvoice;

    @Transient
    private Double unpaid;

    @Transient
    private Date lastPaid;

    @ToString.Exclude
    @JsonIgnore
    @OneToOne(mappedBy = "payment")
    private Sales sales;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false)
    private Date createdDate = new Date();

    public Payment(Integer id) {
        this.id = id;
    }

    public Payment(Double lastPrice) {
        this.lastPrice = lastPrice;
    }
}