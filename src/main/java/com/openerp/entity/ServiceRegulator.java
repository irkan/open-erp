package com.openerp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "service_regulator")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ServiceRegulator {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "service_regulator_seq")
    @SequenceGenerator(sequenceName = "service_regulator_seq", initialValue = 1, allocationSize = 1, name = "service_regulator_seq")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sales_id")
    private Sales sales;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dictionary_service_notification_id")
    private Dictionary serviceNotification;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @Column(name = "serviced_date")
    private Date servicedDate=new Date();

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dictionary_postpone_id")
    private Dictionary postpone;

    @ToString.Exclude
    @Transient
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date servicedDateFrom;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @Column(name = "last_contact_date")
    private Date lastContactDate=new Date();

    @ToString.Exclude
    @Transient
    @JsonIgnore
    private String ids;

    public ServiceRegulator(Sales sales, Dictionary serviceNotification, Date servicedDate) {
        this.sales = sales;
        this.serviceNotification = serviceNotification;
        this.servicedDate = servicedDate;
    }

    public ServiceRegulator(Sales sales, Date servicedDate) {
        this.sales = sales;
        this.servicedDate = servicedDate;
    }
    public ServiceRegulator(Sales sales) {
        this.sales = sales;
    }
}
