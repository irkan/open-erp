package com.openerp.entity;

import com.openerp.util.DateUtility;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Entity
@Table(name = "period")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Period {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "period_seq")
    @SequenceGenerator(sequenceName = "period_seq", allocationSize = 1, name = "period_seq")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User user;

    @Temporal(TemporalType.DATE)
    @Column(name = "start_date")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date startDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "end_date")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date endDate = DateUtility.addYear(1);

    @Pattern(regexp=".{0,250}",message="Maksimum 250 simvol ola bilər")
    @Column(name = "description")
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false)
    private Date createdDate = new Date();
}
