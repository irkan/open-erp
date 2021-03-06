package com.openerp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "inventory")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Inventory {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "inventory_seq")
    @SequenceGenerator(sequenceName = "inventory_seq", allocationSize = 1, name = "inventory_seq")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dictionary_inventory_group_id")
    private Dictionary group;

    @Pattern(regexp=".{2,50}",message="Minimum 2 maksimum 50 simvol ola bilər")
    @Column(name = "name", nullable = false)
    private String name;

    @Pattern(regexp=".{0,250}",message="Maksimum 250 simvol ola bilər")
    @Column(name = "description")
    private String description;

    @Pattern(regexp=".{0,50}",message="Maksimum 50 simvol ola bilər")
    @Column(name = "barcode", nullable = false)
    private String barcode;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @Column(name = "inventory_date", nullable = false)
    private Date inventoryDate = new Date();

    @ToString.Exclude
    @Transient
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date inventoryDateFrom;

    @Transient
    private Boolean old = false;

    @Column(name = "is_active", nullable = false, columnDefinition="boolean default true")
    private Boolean active = true;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false)
    private Date createdDate = new Date();

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "inventory")
    private List<Action> actions;

    @ToString.Exclude
    @Transient
    private Double salePrice=0d;

    public Inventory(Boolean active) {
        this.active = active;
    }

    public Inventory(Integer id, Boolean active) {
        this.id = id;
        this.active = active;
    }

    public Inventory(Integer id) {
        this.id = id;
    }

    public Inventory(Organization organization) {
        this.organization = organization;
    }

    public Inventory(Organization organization, Date inventoryDate) {
        this.organization = organization;
        this.inventoryDate = inventoryDate;
    }
}
