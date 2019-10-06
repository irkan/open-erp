package com.openerp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "payroll_working_hour_record_employee_identifier")
@Getter
@Setter
@NoArgsConstructor
public class WorkingHourRecordEmployeeIdentifier {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "payroll_sequence")
    @SequenceGenerator(sequenceName = "aa_payroll_sequence", allocationSize = 1, name = "payroll_sequence")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "hr_working_hour_record_employee_id")
    private WorkingHourRecordEmployee workingHourRecordEmployee;

    @Pattern(regexp=".{0,8}",message="Maksimum 8 simvol ola bilər")
    @Column(name = "identifier")
    private String identifier;

    public WorkingHourRecordEmployeeIdentifier(WorkingHourRecordEmployee workingHourRecordEmployee, @Pattern(regexp = ".{0,8}", message = "Maksimum 8 simvol ola bilər") String identifier) {
        this.workingHourRecordEmployee = workingHourRecordEmployee;
        this.identifier = identifier;
    }
}
