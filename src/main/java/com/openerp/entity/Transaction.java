package com.openerp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
public class Transaction {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "accounting_sequence")
    @SequenceGenerator(sequenceName = "aa_accounting_sequence", allocationSize = 1, name = "accounting_sequence")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hr_organization_id")
    private Organization organization;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "warehouse_inventory_id")
    private Inventory inventory;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "admin_dictionary_action_id")
    private Dictionary action;

    @Pattern(regexp=".{0,250}",message="Maksimum 250 simvol ola bilər")
    @Column(name = "description")
    private String description;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "accounting_account_id")
    private Account account;

    @Column(name = "amount", nullable = false)
    private int amount=1;

    @Column(name = "is_debt", nullable = false, columnDefinition="boolean default true")
    private Boolean debt = false;

    @Pattern(regexp=".{2,5}",message="Maksimum 5 simvol ola bilər")
    @Column(name = "currency", columnDefinition="varchar(5) default 'AZN'")
    private String currency;

    @Column(name = "rate", columnDefinition="double default 1")
    private double rate;

    @DecimalMin(value = "0", message = "Minimum 0 olmalıdır")
    @Column(name = "price", nullable = false, columnDefinition="Decimal(10,2) default 0")
    private double price=0d;

    @Column(name = "sum_price", nullable = false, columnDefinition="Decimal(10,2) default 0")
    private double sumPrice=0d;

    @Column(name = "balance", nullable = false, columnDefinition="Decimal(10,2) default 0")
    private double balance;

    @Column(name = "is_approve", nullable = false, columnDefinition="boolean default true")
    private Boolean approve = true;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd.MM.yyyy hh:mm:ss")
    @Column(name = "approve_date")
    private Date approveDate;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd.MM.yyyy hh:mm:ss")
    @Column(name = "created_date", nullable = false)
    private Date createdDate = new Date();

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "created_by_admin_user_id")
    private User createdUser;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "transaction_id", referencedColumnName = "id")
    private Transaction transaction;

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
}
