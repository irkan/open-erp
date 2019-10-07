package com.openerp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Entity
@Table(name = "warehouse_action")
@Getter
@Setter
@NoArgsConstructor
public class Action {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "warehouse_sequence")
    @SequenceGenerator(sequenceName = "aa_warehouse_sequence", allocationSize = 1, name = "warehouse_sequence")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "admin_dictionary_action_id")
    private Dictionary action;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hr_warehouse_organization_id")
    private Organization warehouse;

    //@Pattern(regexp="\\d+",message="Say daxil edin")
    @Column(name = "amount")
    private int amount=1;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "warehouse_inventory_id")
    private Inventory inventory;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "warehouse_supplier_id")
    private Supplier supplier;

    @Column(name = "is_active", nullable = false, columnDefinition="boolean default true")
    private Boolean active = true;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false)
    private Date createdDate = new Date();

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "created_by_admin_user_id")
    private User createdUser;

    public Action(Organization warehouse) {
        this.warehouse = warehouse;
    }

    public Action(Dictionary action, Organization warehouse) {
        this.action = action;
        this.warehouse = warehouse;
    }
}