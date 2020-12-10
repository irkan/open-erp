package com.openerp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Entity
@Table(name = "endpoint_detail")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class EndpointDetail {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "endpoint_detail_seq")
    @SequenceGenerator(sequenceName = "endpoint_detail_seq", allocationSize = 1, name = "endpoint_detail_seq")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "endpoint_id")
    private Endpoint endpoint;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    @Column(name = "status_date", nullable = false)
    private Date statusDate=new Date();

    @Transient
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private Date statusDateFrom;

    @Pattern(regexp=".{2,250}",message="Minimum 2 maksimum 250 simvol ola bilər")
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "is_status", nullable = false, columnDefinition="boolean default false")
    private Boolean status = false;

    public EndpointDetail(Endpoint endpoint, Date statusDate, Boolean status, String description) {
        this.endpoint = endpoint;
        this.statusDate = statusDate;
        this.status = status;
        this.description = description;
    }

    public EndpointDetail(Boolean status, Endpoint endpoint) {
        this.status = status;
        this.endpoint = endpoint;
    }


}
