package com.openerp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Date;

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
    @JoinColumn(name = "admin_dictionary_expense_reason_id")
    private Dictionary reason;

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

    @Column(name = "price", nullable = false, columnDefinition="double default 0")
    private double price=0d;

    @Column(name = "sum_price", nullable = false, columnDefinition="double default 0")
    private double sumPrice=calSumPrice(this.debt, this.price, this.amount);

    @Column(name = "balance", nullable = false, columnDefinition="double default 0")
    private double balance;

    @Column(name = "is_approve", nullable = false, columnDefinition="boolean default true")
    private Boolean approve = true;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "approve_date")
    private Date approveDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false)
    private Date createdDate = new Date();

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "created_by_admin_user_id")
    private User createdUser;

    public Transaction(Dictionary reason, @Pattern(regexp = ".{0,250}", message = "Maksimum 250 simvol ola bilər") String description, @Pattern(regexp = ".{0,50}", message = "Maksimum 50 simvol ola bilər") double price) {
        this.reason = reason;
        this.description = description;
        this.price = price;
    }

    public Transaction(@Pattern(regexp = ".{0,250}", message = "Maksimum 250 simvol ola bilər") String description, @Pattern(regexp = ".{0,50}", message = "Maksimum 50 simvol ola bilər") double price, Boolean approve) {
        this.description = description;
        this.price = price;
        this.approve = approve;
    }

    public Transaction(@Pattern(regexp = ".{0,250}", message = "Maksimum 250 simvol ola bilər") String description, Boolean approve) {
        this.description = description;
        this.approve = approve;
    }

    private double calSumPrice(boolean debt, double price, int amount){
        if(!debt){
            return price*amount*(-1.0d);
        }
        return price*amount;
    }
}
