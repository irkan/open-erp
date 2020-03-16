package com.openerp.entity;

import com.openerp.util.RandomString;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.annotation.Primary;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Entity
@Table(name = "accounting_account")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Account {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "accounting_sequence")
    @SequenceGenerator(sequenceName = "aa_accounting_sequence", allocationSize = 1, name = "accounting_sequence")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Pattern(regexp=".{4,17}",message="Minimum 4 maksimum 17 simvol ola bilər")
    @Column(name = "account_number", unique = true, nullable = false)
    private String accountNumber = RandomString.getAlphaNumeric(16);

    @Transient
    @Pattern(regexp=".{4,17}",message="Minimum 4 maksimum 17 simvol ola bilər")
    private String toAccountNumber;

    @Transient
    private String toCurrency;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hr_organization_id")
    private Organization organization;

    @Pattern(regexp=".{0,5}",message="Maksimum 5 simvol ola bilər")
    @Column(name = "currency", nullable = false)
    private String currency = "AZN";

    @Pattern(regexp=".{0,50}",message="Maksimum 50 simvol ola bilər")
    @Column(name = "bank_name")
    private String bankName;

    @Pattern(regexp=".{0,50}",message="Maksimum 50 simvol ola bilər")
    @Column(name = "bank_account_number")
    private String bankAccountNumber;

    @Pattern(regexp=".{0,10}",message="Maksimum 10 simvol ola bilər")
    @Column(name = "bank_account_code")
    private String bankCode;

    @Pattern(regexp=".{0,50}",message="Maksimum 50 simvol ola bilər")
    @Column(name = "bank_swift_bic")
    private String bankSwiftBic;

    @Pattern(regexp=".{0,250}",message="Maksimum 250 simvol ola bilər")
    @Column(name = "description")
    private String description;

    @Column(name = "price", nullable = false, columnDefinition="Decimal(10,2) default 0")
    private double balance=0d;

    @Column(name = "is_active", nullable = false, columnDefinition="boolean default true")
    private Boolean active = true;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false)
    private Date createdDate = new Date();

    public Account(Organization organization, @Pattern(regexp = ".{0,5}", message = "Maksimum 5 simvol ola bilər") String currency, @Pattern(regexp = ".{0,250}", message = "Maksimum 250 simvol ola bilər") String description) {
        this.organization = organization;
        this.currency = currency;
        this.description = description;
    }

    public String getAccountNumberWithCurrency() {
        return currency + " | " + accountNumber;
    }

    public Account(Organization organization) {
        this.organization = organization;
    }
}
