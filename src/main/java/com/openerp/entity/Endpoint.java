package com.openerp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Entity
@Table(name = "admin_endpoint")
@Getter
@Setter
@NoArgsConstructor
public class Endpoint {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "admin_sequence")
    @SequenceGenerator(sequenceName = "aa_admin_sequence", allocationSize = 1, name = "admin_sequence")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="admin_dictionary_connection_type_id", nullable = false)
    private Dictionary connectionType;

    @Pattern(regexp=".{2,50}",message="Minimum 2 maksimum 100 simvol ola bilər")
    @Column(name = "host")
    private String host;

    @Pattern(regexp=".{0,10}",message="Maksimum 100 simvol ola bilər")
    @Column(name = "port")
    private Integer port;

    @Pattern(regexp=".{0,256}",message="Maksimum 256 simvol ola bilər")
    @Column(name = "url")
    private String url;

    @Column(name = "fixed_delay_in_second")
    private Integer fixedDelay;

    @Pattern(regexp=".{2,50}",message="Minimum 2 maksimum 50 simvol ola bilər")
    @Column(name = "description", nullable = false)
    private String description;

    @Transient
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private Date lastStatusDateFrom;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    @Column(name = "last_status_date", nullable = false)
    private Date lastStatusDate = new Date();


    @Column(name = "is_active", nullable = false, columnDefinition="boolean default true")
    private Boolean active = true;
}
