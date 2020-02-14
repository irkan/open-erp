package com.openerp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "sale_service_regulator")
@Getter
@Setter
@NoArgsConstructor
public class ServiceRegulator {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "sale_sequence")
    @SequenceGenerator(sequenceName = "aa_sale_sequence", initialValue = 1, allocationSize = 1, name = "sale_sequence")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sale_sales_id")
    private Sales sales;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "admin_dictionary_service_notification_id")
    private Dictionary serviceNotification;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @Column(name = "serviced_date")
    private Date servicedDate;

    @Transient
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date servicedDateFrom;

    public ServiceRegulator(Sales sales, Dictionary serviceNotification, Date servicedDate) {
        this.sales = sales;
        this.serviceNotification = serviceNotification;
        this.servicedDate = servicedDate;
    }
}
