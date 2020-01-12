package com.openerp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

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
    @JoinColumn(name = "hr_from_organization_id")
    private Organization fromOrganization;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hr_organization_id", nullable = false)
    private Organization organization;

    //@Pattern(regexp="\\d+",message="Say daxil edin")
    @Column(name = "amount")
    private int amount=1;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "warehouse_inventory_id")
    private Inventory inventory;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "warehouse_supplier_id")
    private Supplier supplier;

    @Column(name = "is_approve", nullable = false, columnDefinition="boolean default true")
    private Boolean approve = true;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "approve_date")
    private Date approveDate;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hr_employee_id")
    private Employee employee;

    @Column(name = "is_active", nullable = false, columnDefinition="boolean default true")
    private Boolean active = true;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    @Column(name = "action_date", nullable = false)
    private Date actionDate = new Date();

    @Transient
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private Date actionDateFrom;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false)
    private Date createdDate = new Date();

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "created_by_admin_user_id")
    private User createdUser;

    public Action(Organization organization) {
        this.organization = organization;
    }

    public Action(Dictionary action, Organization organization) {
        this.action = action;
        this.organization = organization;
    }

    public Action(Dictionary action, Organization organization, int amount, Inventory inventory, Supplier supplier, Boolean approve) {
        this.action = action;
        this.organization = organization;
        this.amount = amount;
        this.inventory = inventory;
        this.supplier = supplier;
        this.approve = approve;
    }

    public Action(Employee employee, Dictionary action) {
        this.employee = employee;
        this.action = action;
    }
}
