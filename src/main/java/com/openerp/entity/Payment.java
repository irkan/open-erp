package com.openerp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.openerp.domain.Schedule;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "sale_payment")
@Getter
@Setter
@NoArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "sale_sequence")
    @SequenceGenerator(sequenceName = "aa_sale_sequence", allocationSize = 1, name = "sale_sequence")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @DecimalMin(value = "0", message = "Minimum 0 olmalıdır")
    @Column(name = "price", nullable = false, columnDefinition="Decimal(10,2) default 0")
    private Double price=0d;

    @DecimalMin(value = "0", message = "Minimum 0 olmalıdır")
    @Column(name = "last_price", nullable = false, columnDefinition="Decimal(10,2) default 0")
    private Double lastPrice=0d;

    @Transient
    private Double lastPriceFrom;

    @DecimalMin(value = "0", message = "Minimum 0 olmalıdır")
    @Column(name = "down", nullable = false, columnDefinition="Decimal(10,2) default 0")
    private Double down=0d;

    @DecimalMin(value = "0", message = "Minimum 0 olmalıdır")
    @Column(name = "schedule_price", columnDefinition="Decimal(10,2) default 0")
    private Double schedulePrice;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "admin_dictionary_payment_schedule_id")
    private Dictionary schedule;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "admin_dictionary_payment_period_id")
    private Dictionary period;

    @Pattern(regexp=".{0,10}", message="Maksimum 10 simvol ola bilər")
    @Column(name = "discount")
    private String discount;

    @Pattern(regexp=".{0,250}", message="Maksimum 250 simvol ola bilər")
    @Column(name = "description")
    private String description;

    @Column(name = "is_active", nullable = false, columnDefinition="boolean default true")
    private Boolean active = true;

    @Column(name = "is_cash", nullable = false, columnDefinition="boolean default false")
    private Boolean cash = false;

    @OneToMany(mappedBy = "payment", fetch = FetchType.LAZY)
    private List<PaymentRegulatorNote> paymentRegulatorNotes;

    @JsonIgnore
    @OneToOne(mappedBy = "payment")
    private Sales sales;

    @Column(name = "priority")
    private int priority=5;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false)
    private Date createdDate = new Date();

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "created_by_admin_user_id")
    private User createdUser;

    public Payment(Integer id) {
        this.id = id;
    }
}