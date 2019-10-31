package com.openerp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Entity
@Table(name = "payroll_advance_detail")
@Getter
@Setter
@NoArgsConstructor
public class AdvanceDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "payroll_sequence")
    @SequenceGenerator(sequenceName = "aa_payroll_sequence", allocationSize = 1, name = "payroll_sequence")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "payroll_advance_id")
    private Advance advance;

    @Temporal(TemporalType.DATE)
    @Column(name = "advance_date")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date advanceDate;

    @Column(name = "payed", columnDefinition="Decimal(10,2) default 0")
    private double payed=0d;

    public AdvanceDetail(Advance advance, Date advanceDate, double payed) {
        this.advance = advance;
        this.advanceDate = advanceDate;
        this.payed = payed;
    }
}
