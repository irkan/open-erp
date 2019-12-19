package com.openerp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "payroll_working_hour_record")
@Getter
@Setter
@NoArgsConstructor
public class WorkingHourRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "payroll_sequence")
    @SequenceGenerator(sequenceName = "aa_payroll_sequence", allocationSize = 1, name = "payroll_sequence")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "monthYear")
    private String monthYear=String.valueOf((new Date()).getYear()+1900)+"-"+String.valueOf((new Date()).getMonth()+1);

    @Column(name = "month")
    private int month=(new Date()).getMonth()+1;

    @Column(name = "year")
    private int year=(new Date()).getYear()+1900;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hr_organization_id")
    private Organization organization;

    @OneToMany(mappedBy = "workingHourRecord")
    private List<WorkingHourRecordEmployee> workingHourRecordEmployees;

    @Column(name = "is_approve", nullable = false, columnDefinition="boolean default true")
    private Boolean approve = false;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "approve_date")
    private Date approveDate;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "approved_by_admin_user_id")
    private User approvedUser;

    @Column(name = "is_active", nullable = false, columnDefinition="boolean default true")
    private Boolean active = true;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false)
    private Date createdDate = new Date();

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "created_by_admin_user_id")
    private User createdUser;

    public WorkingHourRecord(int month, int year, Organization organization) {
        this.month = month;
        this.year = year;
        this.organization = organization;
    }

    public WorkingHourRecord(Organization organization) {
        this.organization = organization;
    }
}
