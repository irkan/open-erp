package com.openerp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "accounting_transaction")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Transaction {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "accounting_sequence")
    @SequenceGenerator(sequenceName = "aa_accounting_sequence", allocationSize = 1, name = "accounting_sequence")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hr_organization_id")
    private Organization organization;

    @ToString.Exclude
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "warehouse_inventory_id")
    private Inventory inventory;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "admin_dictionary_action_id")
    private Dictionary action;

    @Pattern(regexp=".{0,500}",message="Maksimum 500 simvol ola bilər")
    @Column(name = "description")
    private String description;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "accounting_account_id")
    private Account account;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "is_debt", nullable = false, columnDefinition="boolean default true")
    private Boolean debt = false;

    @Pattern(regexp=".{2,5}",message="Maksimum 5 simvol ola bilər")
    @Column(name = "currency", columnDefinition="varchar(5) default 'AZN'")
    private String currency;

    @Column(name = "rate", columnDefinition="double default 1")
    private Double rate;

    @DecimalMin(value = "0", message = "Minimum 0 olmalıdır")
    @Column(name = "price", nullable = false, columnDefinition="Decimal(10,2) default 0")
    private Double price=0d;

    @ToString.Exclude
    @Transient
    private Double priceFrom;

    @Column(name = "sum_price", nullable = false, columnDefinition="Decimal(10,2) default 0")
    private Double sumPrice=0d;

    @Column(name = "balance", columnDefinition="Decimal(10,2) default 0")
    private Double balance;

    @Column(name = "is_approve", nullable = false, columnDefinition="boolean default true")
    private Boolean approve = true;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    @Column(name = "transaction_date", nullable = false)
    private Date transactionDate = new Date();

    @ToString.Exclude
    @Transient
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private Date transactionDateFrom;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    @Column(name = "approve_date")
    private Date approveDate;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    @Column(name = "created_date", nullable = false)
    private Date createdDate = new Date();

    @Column(name = "is_accountable", nullable = false, columnDefinition="boolean default true")
    private Boolean accountable = true;

    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "transaction_id", referencedColumnName = "id")
    private Transaction transaction;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="transaction")
    private List<Transaction> children;


    public Transaction(Organization organization, Inventory inventory, Dictionary action, @Pattern(regexp = ".{0,250}", message = "Maksimum 250 simvol ola bilər") String description, Boolean approve, Transaction transaction) {
        this.organization = organization;
        this.inventory = inventory;
        this.action = action;
        this.description = description;
        this.approve = approve;
        this.transaction = transaction;
    }

    public Transaction(Organization organization) {
        this.organization = organization;
    }

    public Transaction(Organization organization, Dictionary action, Boolean approve) {
        this.organization = organization;
        this.action = action;
        this.approve = approve;
    }

    public Transaction(Organization organization, Double price) {
        this.organization = organization;
        this.price = price;
    }


}
