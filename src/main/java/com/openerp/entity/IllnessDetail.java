package com.openerp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Entity
@Table(name = "illness_detail")
@Getter
@Setter
@NoArgsConstructor
public class IllnessDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "illness_detail_seq")
    @SequenceGenerator(sequenceName = "illness_detail_seq", allocationSize = 1, name = "illness_detail_seq")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Temporal(TemporalType.DATE)
    @Column(name = "illness_date", nullable = false)
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date illnessDate;

    @Pattern(regexp=".{0,10}",message="Maksimum 10 simvol ola bilər")
    @Column(name = "identifier")
    private String identifier;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "illness_id")
    private Illness illness;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    public IllnessDetail(String identifier, Date illnessDate, Illness illness, Employee employee) {
        this.identifier = identifier;
        this.illnessDate = illnessDate;
        this.illness = illness;
        this.employee = employee;
    }
}
