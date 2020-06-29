package com.openerp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "sales_inventory")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class SalesInventory {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "sales_inventory_seq")
    @SequenceGenerator(sequenceName = "sales_inventory_seq", initialValue = 1, allocationSize = 1, name = "sales_inventory_seq")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "inventory_id")
    private Inventory inventory;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dictionary_sales_type_id")
    private Dictionary salesType;

    @ToString.Exclude
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sales_id")
    private Sales sales;

    @Column(name = "is_active", nullable = false, columnDefinition="boolean default true")
    private Boolean active = true;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false)
    private Date createdDate = new Date();

    public SalesInventory(Inventory inventory, Sales sales) {
        this.inventory = inventory;
        this.sales = sales;
    }

    public SalesInventory(Inventory inventory, Sales sales, Dictionary salesType) {
        this.inventory = inventory;
        this.sales = sales;
        this.salesType = salesType;
    }
}
