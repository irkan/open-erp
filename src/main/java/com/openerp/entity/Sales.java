package com.openerp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "sales")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Sales {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "sales_seq")
    @SequenceGenerator(sequenceName = "sales_seq", initialValue = 100001, allocationSize = 1, name = "sales_seq")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "is_service", nullable = false, columnDefinition="boolean default false")
    private Boolean service = false;

    @Column(name = "is_approve", nullable = false, columnDefinition="boolean default false")
    private Boolean approve = false;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "approve_date")
    private Date approveDate;

    @ToString.Exclude
    @Transient
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date approveDateFrom;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "crm_customer_id", nullable = false)
    private Customer customer;

    @OneToMany(mappedBy = "sales", fetch = FetchType.LAZY)
    private List<SalesInventory> salesInventories;

    @JsonIgnore
    @OneToMany(mappedBy = "sales", fetch = FetchType.LAZY)
    private List<ContactHistory> contactHistories;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_console_id")
    private Employee console;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_van_leader_id")
    private Employee vanLeader;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_dealer_id")
    private Employee dealer;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_canavasser_id")
    private Employee canavasser;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_servicer_id")
    private Employee servicer;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tax_configuration_id")
    private TaxConfiguration taxConfiguration;

    @ToString.Exclude
    @JsonIgnore
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "sales")
    private List<Invoice> invoices;

    @ToString.Exclude
    @JsonIgnore
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "sales", cascade = CascadeType.ALL)
    private List<ServiceRegulator> serviceRegulators;

    @Column(name = "guarantee", nullable = false)
    private Integer guarantee;

    @Temporal(TemporalType.DATE)
    @Column(name = "date", nullable = false)
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date saleDate = new Date();

    @ToString.Exclude
    @Transient
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date saleDateFrom;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @Column(name = "guarantee_expire", nullable = false)
    private Date guaranteeExpire;

    @ToString.Exclude
    @Transient
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date guaranteeExpireFrom;

    @Column(name = "is_saled", nullable = false, columnDefinition="boolean default false")
    private Boolean saled = false;

    @Column(name = "is_returned", nullable = false, columnDefinition="boolean default false")
    private Boolean returned = false;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @Column(name = "returned_date")
    private Date returnedDate;

    @Column(name = "is_court", nullable = false, columnDefinition="boolean default false")
    private Boolean court = false;

    @Column(name = "is_execute", nullable = false, columnDefinition="boolean default false")
    private Boolean execute = false;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @Column(name = "execute_date")
    private Date executeDate;

    @ToString.Exclude
    @Transient
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date executeDateFrom;

    @ToString.Exclude
    @Transient
    private String description;

    @Column(name = "is_not_service_next", nullable = false, columnDefinition="boolean default false")
    private Boolean notServiceNext = false;

    @Column(name = "not_service_next_reason")
    private String notServiceNextReason;

    @Column(name = "service_notice")
    private String serviceNotice;

    @Column(name = "is_active", nullable = false, columnDefinition="boolean default true")
    private Boolean active = true;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false)
    private Date createdDate = new Date();

    public Sales(Integer id) {
        this.id = id;
    }

    public Sales(Organization organization, Boolean service) {
        this.organization = organization;
        this.service = service;
    }

    public Sales(Integer id, Organization organization, Boolean service) {
        this.id = id;
        this.organization = organization;
        this.service = service;
    }

    public Sales(Integer id, Organization organization, Boolean service, Date saleDate) {
        this.id = id;
        this.organization = organization;
        this.service = service;
        this.saleDate = saleDate;
    }

    public Sales(Organization organization) {
        this.organization = organization;
    }

    public Sales(Integer id, Organization organization) {
        this.id = id;
        this.organization = organization;
    }
}
