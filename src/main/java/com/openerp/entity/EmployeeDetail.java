package com.openerp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Entity
@Table(name = "hr_employee_detail")
@Getter
@Setter
@NoArgsConstructor
public class EmployeeDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "hr_sequence")
    @SequenceGenerator(sequenceName = "aa_hr_sequence", allocationSize = 1, name = "hr_sequence")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hr_employee_id")
    private Employee employee;

    @Column(name = "name")
    private String name;

    @Column(name = "value")
    private String value;

    @Pattern(regexp=".{0,255}",message="Maksimum 255 simvol ola bilər")
    @Column(name = "description")
    private String description;

    @Column(name = "is_active", nullable = false, columnDefinition="boolean default true")
    private Boolean active = true;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false)
    private Date createdDate = new Date();

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "created_by_admin_user_id")
    private User createdUser;





/*    @Column(name = "previous_sum_work_experience_year")
    private int previousSumWorkExperienceYear=0;

    @Column(name = "calculated_vacation_day")
    private int calculatedVacationDay=0;

    @Column(name = "gross_salary_official_part")
    private double salaryOfficial=0d;

    @Column(name = "gross_salary_non_official_part")
    private double salaryNonOfficial=0d;

    @Column(name = "percent_from_sale")
    private double percentFromSale=0d;

    @Column(name = "percent_from_sale")
    private double hemkarlardanTutulanMebleg=0d;

    public EmployeeDetail(int previousSumWorkExperienceYear, int calculatedVacationDay, double salaryOfficial, double salaryNonOfficial) {
        this.previousSumWorkExperienceYear = previousSumWorkExperienceYear;
        this.calculatedVacationDay = calculatedVacationDay;
        this.salaryOfficial = salaryOfficial;
        this.salaryNonOfficial = salaryNonOfficial;
    }*/

    public EmployeeDetail(Employee employee, String name, String value, @Pattern(regexp = ".{0,255}", message = "Maksimum 255 simvol ola bilər") String description, Boolean active) {
        this.employee = employee;
        this.name = name;
        this.value = value;
        this.description = description;
        this.active = active;
    }
}
