package com.openerp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Entity
@Table(name = "accounting_financing")
@Getter
@Setter
@NoArgsConstructor
public class Financing {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "accounting_sequence")
    @SequenceGenerator(sequenceName = "aa_accounting_sequence", allocationSize = 1, name = "accounting_sequence")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "warehouse_inventory_id")
    private Inventory inventory;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "warehouse_organization_id")
    private Organization organization;

    @Transient
    private Double priceFrom;

    @Column(name="price")
    private Double price=0d;

    @Pattern(regexp=".{0,50}",message="Maksimum 5 simvol ola bilər")
    @Column(name = "currency", columnDefinition="varchar(5) default 'AZN'")
    private String currency="AZN";

    @Column(name = "is_active", nullable = false, columnDefinition="boolean default true")
    private Boolean active = true;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    @Column(name = "financing_date", nullable = false)
    private Date financingDate = new Date();

    @Transient
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private Date financingDateFrom;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    @Column(name = "created_date", nullable = false)
    private Date createdDate = new Date();

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "created_by_admin_user_id")
    private User createdUser;

    public Financing(Inventory inventory, double price, Organization organization) {
        this.inventory = inventory;
        this.price = price;
        this.organization = organization;
    }

    public Financing(Organization organization) {
        this.organization = organization;
    }
}
