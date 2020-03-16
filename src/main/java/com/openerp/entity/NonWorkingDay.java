package com.openerp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Entity
@Table(name = "hr_non_working_day")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class NonWorkingDay {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "hr_sequence")
    @SequenceGenerator(sequenceName = "aa_hr_sequence", allocationSize = 1, name = "hr_sequence")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @NotNull(message = "Boş olmamalıdır")
    @Temporal(TemporalType.DATE)
    @Column(name = "non_working_date", nullable = false)
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date nonWorkingDate;

    @ToString.Exclude
    @Transient
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date nonWorkingDateFrom;

    @Pattern(regexp=".{1,6}",message="Minimum 1 maksimum 6 simvol ola bilər")
    @Column(name = "identifier")
    private String identifier;

    @Pattern(regexp=".{0,250}",message="Maksimum 250 simvol ola bilər")
    @Column(name = "description")
    private String description;

    @Column(name = "is_active", nullable = false, columnDefinition="boolean default true")
    private Boolean active = true;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false)
    private Date createdDate = new Date();
}
