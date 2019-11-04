package com.openerp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "crm_customer")
@Getter
@Setter
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "crm_sequence")
    @SequenceGenerator(sequenceName = "aa_crm_sequence", allocationSize = 1, name = "crm_sequence")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "common_person_id", nullable = false)
    private Person person;

    @Temporal(TemporalType.DATE)
    @Column(name = "contract_date")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date contractDate = new Date();
}
