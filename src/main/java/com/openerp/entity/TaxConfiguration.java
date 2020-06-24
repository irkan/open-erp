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
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "admin_tax_configuration")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class TaxConfiguration {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "admin_tax_configuration_sequence")
    @SequenceGenerator(sequenceName = "aa_admin_tax_configuration_sequence", initialValue = 10, allocationSize = 1, name = "admin_tax_configuration_sequence")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hr_organization_id", nullable = false)
    private Organization organization;

    @Pattern(regexp=".{3,15}",message="Minimum 5 maksimum 15 simvol ola bilər")
    @Column(name = "voen", nullable = false)
    private String voen;

    @Pattern(regexp=".{0,250}",message="Minimum 0 maksimum 250 simvol ola bilər")
    @Column(name = "company", nullable = false)
    private String company;

    @Pattern(regexp=".{0,250}",message="Minimum 0 maksimum 250 simvol ola bilər")
    @Column(name = "description")
    private String description;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "common_person_id")
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

    public TaxConfiguration(Organization organization) {
        this.organization = organization;
    }
}
