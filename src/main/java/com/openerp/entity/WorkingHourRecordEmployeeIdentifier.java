package com.openerp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "working_hour_record_employee_identifier")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class WorkingHourRecordEmployeeIdentifier {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "working_hour_record_employee_identifier_seq")
    @SequenceGenerator(sequenceName = "working_hour_record_employee_identifier_seq", allocationSize = 1, name = "working_hour_record_employee_identifier_seq")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "working_hour_record_employee_id")
    private WorkingHourRecordEmployee workingHourRecordEmployee;

    @Pattern(regexp=".{0,8}",message="Maksimum 8 simvol ola bilər")
    @Column(name = "identifier")
    private String identifier;

    @Column(name = "month_day")
    private int monthDay;

    @Column(name = "week_day")
    private int weekDay;

    public WorkingHourRecordEmployeeIdentifier(WorkingHourRecordEmployee workingHourRecordEmployee, @Pattern(regexp = ".{0,8}", message = "Maksimum 8 simvol ola bilər") String identifier, int monthDay, int weekDay) {
        this.workingHourRecordEmployee = workingHourRecordEmployee;
        this.identifier = identifier;
        this.monthDay = monthDay;
        this.weekDay = weekDay;
    }
}
