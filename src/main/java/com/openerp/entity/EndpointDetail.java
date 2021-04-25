package com.openerp.entity;

import lombok.*;
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

    @Transient
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private Date downDateFrom;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    @Column(name = "down_date", nullable = false)
    private Date downDate;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    @Column(name = "up_date")
    private Date upDate;

    @Column(name = "different")
    private Long different;

    @Pattern(regexp=".{2,250}",message="Minimum 2 maksimum 250 simvol ola bilər")
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "is_active", nullable = false, columnDefinition="boolean default true")
    private Boolean active = true;

    public EndpointDetail(Endpoint endpoint, String description) {
        this.endpoint = endpoint;
        this.description = description;
    }
}
