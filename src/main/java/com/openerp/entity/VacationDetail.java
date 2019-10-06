package com.openerp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Entity
@Table(name = "hr_vacation_detail")
@Getter
@Setter
@NoArgsConstructor
public class VacationDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "hr_sequence")
    @SequenceGenerator(sequenceName = "aa_hr_sequence", allocationSize = 1, name = "hr_sequence")
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
    @JoinColumn(name = "hr_vacation_id")
    private Vacation vacation;

    public VacationDetail(String identifier, Date vacationDate, Vacation vacation) {
        this.identifier = identifier;
        this.vacationDate = vacationDate;
        this.vacation = vacation;
    }
}
