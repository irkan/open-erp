package com.openerp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Entity
@Table(name = "vacation_detail")
@Getter
@Setter
@NoArgsConstructor
public class VacationDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "vacation_detail_seq")
    @SequenceGenerator(sequenceName = "vacation_detail_seq", allocationSize = 1, name = "vacation_detail_seq")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Temporal(TemporalType.DATE)
    @Column(name = "vacation_date", nullable = false)
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date vacationDate;

    @Pattern(regexp=".{0,10}",message="Maksimum 10 simvol ola bilər")
    @Column(name = "identifier")
    private String identifier;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vacation_id")
    private Vacation vacation;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    public VacationDetail(String identifier, Date vacationDate, Vacation vacation, Employee employee) {
        this.identifier = identifier;
        this.vacationDate = vacationDate;
        this.vacation = vacation;
        this.employee = employee;
    }
}
