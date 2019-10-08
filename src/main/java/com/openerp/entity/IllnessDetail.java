package com.openerp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Entity
@Table(name = "hr_illness_detail")
@Getter
@Setter
@NoArgsConstructor
public class IllnessDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "hr_sequence")
    @SequenceGenerator(sequenceName = "aa_hr_sequence", allocationSize = 1, name = "hr_sequence")
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
    @JoinColumn(name = "hr_illness_id")
    private Illness illness;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hr_employee_id", nullable = false)
    private Employee employee;

    public IllnessDetail(String identifier, Date illnessDate, Illness illness, Employee employee) {
        this.identifier = identifier;
        this.illnessDate = illnessDate;
        this.illness = illness;
        this.employee = employee;
    }
}
