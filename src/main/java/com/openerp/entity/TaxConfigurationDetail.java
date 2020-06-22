package com.openerp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Entity
@Table(name = "accounting_tax_configuration_detail")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class TaxConfigurationDetail {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "accounting_tax_configuration_detail_sequence")
    @SequenceGenerator(sequenceName = "aa_accounting_tax_configuration_detail_sequence", initialValue = 10, allocationSize = 1, name = "accounting_tax_configuration_detail_sequence")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "accounting_tax_configuration_id")
    private TaxConfiguration taxConfiguration;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sale_sales_id", nullable = false)
    private Sales sales;

    @Column(name = "is_active", nullable = false, columnDefinition="boolean default true")
    private Boolean active = true;

}
