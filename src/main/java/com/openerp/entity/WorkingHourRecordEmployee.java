package com.openerp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "payroll_working_hour_record_employee")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class WorkingHourRecordEmployee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "payroll_sequence")
    @SequenceGenerator(sequenceName = "aa_payroll_sequence", allocationSize = 1, name = "payroll_sequence")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @ToString.Exclude
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "payroll_working_hour_record_id")
    private WorkingHourRecord workingHourRecord;

    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hr_employee_id")
    private Employee employee;

    @JsonIgnore
    @OneToMany(mappedBy = "workingHourRecordEmployee", fetch = FetchType.EAGER)
    private List<WorkingHourRecordEmployeeIdentifier> workingHourRecordEmployeeIdentifiers;

    @JsonIgnore
    @OneToMany(mappedBy = "workingHourRecordEmployee")
    private List<WorkingHourRecordEmployeeDayCalculation> workingHourRecordEmployeeDayCalculations;

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
