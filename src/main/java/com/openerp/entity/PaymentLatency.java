package com.openerp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "payment_latency")
@Getter
@Setter
@NoArgsConstructor
public class PaymentLatency {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "payment_latency_seq")
    @SequenceGenerator(sequenceName = "payment_latency_seq", initialValue = 1, allocationSize = 1, name = "payment_latency_seq")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "sales_id")
    private Integer salesId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    @Column(name = "task_date")
    private Date taskDate;

    @Column(name = "latency_day")
    private Integer latencyDay;

    @Column(name = "latency_sum")
    private Double latencySum;

    public PaymentLatency(Organization organization, Date taskDate, Integer salesId) {
        this.organization = organization;
        this.taskDate = taskDate;
        this.salesId = salesId;
    }
}
