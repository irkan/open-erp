package com.openerp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Entity
@Table(name = "financing")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Financing {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "financing_seq")
    @SequenceGenerator(sequenceName = "financing_seq", allocationSize = 1, name = "financing_seq")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "inventory_id")
    private Inventory inventory;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @ToString.Exclude
    @Transient
    private Double priceFrom;

    @Column(name="price")
    private Double price=0d;

    @ToString.Exclude
    @Transient
    private Double salePriceFrom;

    @Column(name="sale_price")
    private Double salePrice=0d;

    @Pattern(regexp=".{0,50}",message="Maksimum 5 simvol ola bilər")
    @Column(name = "currency", columnDefinition="varchar(5) default 'AZN'")
    private String currency="AZN";

    @Column(name = "is_active", nullable = false, columnDefinition="boolean default true")
    private Boolean active = true;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    @Column(name = "financing_date", nullable = false)
    private Date financingDate = new Date();

    @ToString.Exclude
    @Transient
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private Date financingDateFrom;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    @Column(name = "created_date", nullable = false)
    private Date createdDate = new Date();

    public Financing(Inventory inventory, double price, Double salePrice, Organization organization) {
        this.inventory = inventory;
        this.price = price;
        this.salePrice = salePrice;
        this.organization = organization;
    }

    public Financing(Organization organization, Double price, Double salePrice, @Pattern(regexp = ".{0,50}", message = "Maksimum 5 simvol ola bilər") String currency) {
        this.organization = organization;
        this.price = price;
        this.salePrice = salePrice;
        this.currency = currency;
    }

    public Financing(Organization organization) {
        this.organization = organization;
    }
}
