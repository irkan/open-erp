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

@Entity
@Table(name = "action")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Action {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "action_seq")
    @SequenceGenerator(sequenceName = "action_seq", allocationSize = 1, name = "action_seq")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dictionary_action_id")
    private Dictionary action;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "from_organization_id")
    private Organization fromOrganization;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    //@Pattern(regexp="\\d+",message="Say daxil edin")
    @Column(name = "amount")
    private Integer amount;

    @Pattern(regexp=".{0,250}",message="Maksimum 250 simvol ola bilər")
    @Column(name = "location_row")
    private String row;

    @Pattern(regexp=".{0,250}",message="Maksimum 250 simvol ola bilər")
    @Column(name = "location_column")
    private String column;

    @ToString.Exclude
    @Transient
    private Integer amountFrom;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "inventory_id")
    private Inventory inventory;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @Column(name = "is_approve", nullable = false, columnDefinition="boolean default true")
    private Boolean approve = true;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "approve_date")
    private Date approveDate;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Pattern(regexp=".{0,250}",message="Maksimum 250 simvol ola bilər")
    @Column(name = "description")
    private String description;

    @Column(name = "is_old", nullable = false, columnDefinition="boolean default false")
    private Boolean old = false;

    @Column(name = "is_active", nullable = false, columnDefinition="boolean default true")
    private Boolean active = true;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    @Column(name = "action_date", nullable = false)
    private Date actionDate = new Date();

    @ToString.Exclude
    @Transient
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private Date actionDateFrom;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false)
    private Date createdDate = new Date();

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

    public Action(Employee employee, Dictionary action, Date actionDate) {
        this.employee = employee;
        this.action = action;
        this.actionDate = actionDate;
    }

    public Action(Employee employee, Dictionary action, Date actionDate, Integer amountFrom) {
        this.employee = employee;
        this.action = action;
        this.actionDate = actionDate;
        this.amountFrom = amountFrom;
    }

    public Action(Employee employee, Dictionary action, Date actionDate, Integer amountFrom, Organization organization) {
        this.employee = employee;
        this.action = action;
        this.actionDate = actionDate;
        this.amountFrom = amountFrom;
        this.organization = organization;
    }

    public Action(Inventory inventory, Organization organization) {
        this.inventory = inventory;
        this.organization = organization;
    }

    public Action(Dictionary action, Inventory inventory, Organization organization) {
        this.action = action;
        this.inventory = inventory;
        this.organization = organization;
    }
}
