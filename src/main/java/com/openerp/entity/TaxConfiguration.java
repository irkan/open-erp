package com.openerp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.openerp.util.Util;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tax_configuration")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class TaxConfiguration {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "tax_configuration_seq")
    @SequenceGenerator(sequenceName = "tax_configuration_seq", initialValue = 101, allocationSize = 1, name = "tax_configuration_seq")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    @Pattern(regexp=".{3,15}",message="Minimum 5 maksimum 15 simvol ola bilər")
    @Column(name = "voen", nullable = false)
    private String voen;

    @Pattern(regexp=".{0,250}",message="Minimum 0 maksimum 250 simvol ola bilər")
    @Column(name = "company")
    private String company;

    @Pattern(regexp=".{0,250}",message="Minimum 0 maksimum 250 simvol ola bilər")
    @Column(name = "description")
    private String description;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id")
    private Person person;

    @DecimalMin(value = "0", message = "Minimum 0 olmalıdır")
    @Column(name = "max_limit_monthly", nullable = false, columnDefinition="Decimal(10,2) default 0")
    private Double maxLimitMonthly=0d;

    @Transient
    private Double plannedPaymentAmountMonthly;

    @Transient
    private Double payedAmountMonthly;

    @Transient
    private Integer salesCount;

    @Column(name = "is_active", nullable = false, columnDefinition="boolean default true")
    private Boolean active = true;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false)
    private Date createdDate = new Date();

    @JsonIgnore
    public String getLabel() {
        return voen + " - " + person.getFullName();
    }

    public TaxConfiguration(Organization organization) {
        this.organization = organization;
    }

    public TaxConfiguration(Organization organization, @Pattern(regexp = ".{3,15}", message = "Minimum 5 maksimum 15 simvol ola bilər") String voen, Person person, @DecimalMin(value = "0", message = "Minimum 0 olmalıdır") Double maxLimitMonthly) {
        this.organization = organization;
        this.voen = voen;
        this.person = person;
        this.maxLimitMonthly = maxLimitMonthly;
    }
}
