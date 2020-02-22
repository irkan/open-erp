package com.openerp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "sale_sales_inventory")
@Getter
@Setter
@NoArgsConstructor
public class SalesInventory {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "sale_sequence")
    @SequenceGenerator(sequenceName = "aa_sale_sequence", initialValue = 1, allocationSize = 1, name = "sale_sequence")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "warehouse_inventory_id")
    private Inventory inventory;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "admin_dictionary_sales_type_id")
    private Dictionary salesType;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sale_sales_id")
    private Sales sales;

    @Column(name = "is_active", nullable = false, columnDefinition="boolean default true")
    private Boolean active = true;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false)
    private Date createdDate = new Date();

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "created_by_admin_user_id")
    private User createdUser;

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
