package com.openerp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "payroll_working_hour_record_employee_day_calculation")
@Getter
@Setter
@NoArgsConstructor
public class WorkingHourRecordEmployeeDayCalculation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "payroll_sequence")
    @SequenceGenerator(sequenceName = "aa_payroll_sequence", allocationSize = 1, name = "payroll_sequence")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hr_working_hour_record_employee_id")
    private WorkingHourRecordEmployee workingHourRecordEmployee;

    @Pattern(regexp=".{0,255}",message="Maksimum 255 simvol ola bilər")
    @Column(name = "description")
    private String description;

    @Pattern(regexp=".{0,100}",message="Maksimum 100 simvol ola bilər")
    @Column(name = "key_field")
    private String key;

    @Column(name = "value")
    private double value=0d;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "admin_dictionary_identifier_id")
    private Dictionary identifier;

    public WorkingHourRecordEmployeeDayCalculation(WorkingHourRecordEmployee workingHourRecordEmployee, @Pattern(regexp = ".{0,255}", message = "Maksimum 255 simvol ola bilər") String description, @Pattern(regexp = ".{0,100}", message = "Maksimum 100 simvol ola bilər") String key, int value, Dictionary identifier) {
        this.workingHourRecordEmployee = workingHourRecordEmployee;
        this.description = description;
        this.key = key;
        this.value = value;
        this.identifier = identifier;
    }
}
