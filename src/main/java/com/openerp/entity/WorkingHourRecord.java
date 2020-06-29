package com.openerp.entity;

import com.openerp.util.DateUtility;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "working_hour_record")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class WorkingHourRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "working_hour_record_seq")
    @SequenceGenerator(sequenceName = "working_hour_record_seq", allocationSize = 1, name = "working_hour_record_seq")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "monthYear")
    private String monthYear= DateUtility.getYearMonth(new Date()); //String.valueOf((new Date()).getYear()+1900)+"-"+String.valueOf((new Date()).getMonth()+1);

    @Column(name = "month")
    private int month=(new Date()).getMonth()+1;

    @Column(name = "year")
    private int year=(new Date()).getYear()+1900;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @OneToMany(mappedBy = "workingHourRecord")
    private List<WorkingHourRecordEmployee> workingHourRecordEmployees;

    @Column(name = "is_approve", nullable = false, columnDefinition="boolean default true")
    private Boolean approve = false;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "approve_date")
    private Date approveDate;

    @Column(name = "is_active", nullable = false, columnDefinition="boolean default true")
    private Boolean active = true;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false)
    private Date createdDate = new Date();

    public WorkingHourRecord(int month, int year, Organization organization) {
        this.month = month;
        this.year = year;
        this.organization = organization;
    }

    public WorkingHourRecord(Organization organization) {
        this.organization = organization;
    }
}
