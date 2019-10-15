package com.openerp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "payroll_working_hour_record_employee")
@Getter
@Setter
@NoArgsConstructor
public class WorkingHourRecordEmployee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "payroll_sequence")
    @SequenceGenerator(sequenceName = "aa_payroll_sequence", allocationSize = 1, name = "payroll_sequence")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "payroll_working_hour_record_id")
    private WorkingHourRecord workingHourRecord;

    @Column(name = "work_days_in_month")
    private int workDaysInMonth=0;

    @Column(name = "work_duty_days_in_month")
    private int workDutyDaysInMonth=0;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hr_employee_id")
    private Employee employee;

    @OneToMany(mappedBy = "workingHourRecordEmployee", fetch = FetchType.EAGER)
    private List<WorkingHourRecordEmployeeIdentifier> workingHourRecordEmployeeIdentifiers;

    @Pattern(regexp=".{0,255}",message="Maksimum 255 simvol ola bilər")
    @Column(name = "full_name")
    private String fullName;

    @Pattern(regexp=".{0,255}",message="Maksimum 255 simvol ola bilər")
    @Column(name = "position")
    private String position;

    @Pattern(regexp=".{0,255}",message="Maksimum 255 simvol ola bilər")
    @Column(name = "organization")
    private String organization;

    public WorkingHourRecordEmployee(WorkingHourRecord workingHourRecord, Employee employee, @Pattern(regexp = ".{0,255}", message = "Maksimum 255 simvol ola bilər") String fullName, @Pattern(regexp = ".{0,255}", message = "Maksimum 255 simvol ola bilər") String position, @Pattern(regexp = ".{0,255}", message = "Maksimum 255 simvol ola bilər") String organization) {
        this.workingHourRecord = workingHourRecord;
        this.employee = employee;
        this.fullName = fullName;
        this.position = position;
        this.organization = organization;
    }
}
