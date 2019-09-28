package com.openerp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Entity
@Table(name = "admin_currency_rate")
@Getter
@Setter
@NoArgsConstructor
public class CurrencyRate {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "admin_sequence")
    @SequenceGenerator(sequenceName = "aa_admin_sequence", allocationSize = 1, name = "admin_sequence")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Temporal(TemporalType.DATE)
    @Column(name = "rate_date")
    private Date rateDate=new Date();

    @Column(name = "type")
    private String type;

    @Pattern(regexp=".{0,10}",message="Maksimum 5 simvol ola bilər")
    @Column(name = "code")
    private String code;

    @Column(name = "nominal")
    private String nominal;

    @Pattern(regexp=".{0,100}",message="Maksimum 100 simvol ola bilər")
    @Column(name = "name")
    private String name;

    @Column(name = "value")
    private double value;
}
