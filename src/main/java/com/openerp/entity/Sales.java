package com.openerp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.openerp.util.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "sale_sales")
@Getter
@Setter
@NoArgsConstructor
public class Sales {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "sale_sequence")
    @SequenceGenerator(sequenceName = "aa_sale_sequence", initialValue = 100001, allocationSize = 1, name = "sale_sequence")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "crm_customer_id", nullable = false)
    private Customer customer;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "warehouse_action_id")
    private Action action;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "hr_organization_id", nullable = false)
    private Organization organization;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "sale_payment_id", nullable = false)
    private Payment payment;

    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hr_employee_console_id")
    private Employee console;

    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hr_employee_van_leader_id")
    private Employee vanLeader;

    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hr_employee_dealer_id")
    private Employee dealer;

    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hr_employee_canavasser_id")
    private Employee canavasser;

    @JsonIgnore
    @OneToMany(mappedBy = "sales")
    private List<Invoice> invoices;

    @Column(name = "guarantee", nullable = false)
    private int guarantee;

    @Temporal(TemporalType.DATE)
    @Column(name = "sale_date", nullable = false)
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date saleDate = new Date();

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @Column(name = "guarantee_expire", nullable = false)
    private Date guaranteeExpire = new Date();

    @Column(name = "is_saled", nullable = false, columnDefinition="boolean default false")
    private Boolean saled = false;

    @Column(name = "is_active", nullable = false, columnDefinition="boolean default true")
    private Boolean active = true;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false)
    private Date createdDate = new Date();

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "created_by_admin_user_id")
    private User createdUser;
}
