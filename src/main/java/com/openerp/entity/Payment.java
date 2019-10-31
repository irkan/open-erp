package com.openerp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Pattern;
import java.util.Date;

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
    private double price=0d;

    @DecimalMin(value = "0", message = "Minimum 0 olmalıdır")
    @Column(name = "last_price", nullable = false, columnDefinition="Decimal(10,2) default 0")
    private double lastPrice=0d;

    @DecimalMin(value = "0", message = "Minimum 0 olmalıdır")
    @Column(name = "down", nullable = false, columnDefinition="Decimal(10,2) default 0")
    private double down=0d;

    @Column(name = "schedule")
    private int schedule=1;

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

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false)
    private Date createdDate = new Date();

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "created_by_admin_user_id")
    private User createdUser;
}
