package com.openerp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "payroll_salary")
@Getter
@Setter
@NoArgsConstructor
public class Salary {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "payroll_sequence")
    @SequenceGenerator(sequenceName = "aa_payroll_sequence", allocationSize = 1, name = "payroll_sequence")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "payroll_working_hour_record_id")
    private WorkingHourRecord workingHourRecord;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hr_organization_id")
    private Organization organization;

    @JsonIgnore
    @OneToMany(mappedBy = "salary", cascade = CascadeType.ALL)
    private List<SalaryEmployee> salaryEmployees;

    @Column(name = "is_approve", nullable = false, columnDefinition="boolean default false")
    private Boolean approve = false;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "approve_date")
    private Date approveDate;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "approved_by_admin_user_id")
    private User approvedUser;

    @Column(name = "is_active", nullable = false, columnDefinition="boolean default true")
    private Boolean active = true;

    @Column(name = "sum_of_membership_fee_for_trade_union")
    private Double sumOfMembershipFeeForTradeUnion;

    @Column(name = "sum_of_compulsory_health_insurance")
    private Double sumOfCompulsoryHealthInsurance;

    @Column(name = "sum_of_unemployment_insurance")
    private Double sumOfUnemploymentInsurance;

    @Column(name = "sum_of_tax")
    private Double sumOfTax;

    @Column(name = "sum_of_dsmf_deduction")
    private Double sumOfDsmfDeduction;

    @Column(name = "sum_of_salary")
    private Double sumOfSalary;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false)
    private Date createdDate = new Date();

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "created_by_admin_user_id")
    private User createdUser;

    public Salary(WorkingHourRecord workingHourRecord) {
        this.workingHourRecord = workingHourRecord;
    }

    public Salary(Organization organization) {
        this.organization = organization;
    }
}
