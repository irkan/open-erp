package com.openerp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "business_trip_detail")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class BusinessTripDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "business_trip_detail_seq")
    @SequenceGenerator(sequenceName = "business_trip_detail_seq", allocationSize = 1, name = "business_trip_detail_seq")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Temporal(TemporalType.DATE)
    @Column(name = "business_trip_date", nullable = false)
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date businessTripDate;

    @Pattern(regexp=".{0,10}",message="Maksimum 10 simvol ola bilər")
    @Column(name = "identifier")
    private String identifier;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "business_trip_id")
    private BusinessTrip businessTrip;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    public BusinessTripDetail(String identifier, Date businessTripDate, BusinessTrip businessTrip, Employee employee) {
        this.identifier = identifier;
        this.businessTripDate = businessTripDate;
        this.businessTrip = businessTrip;
        this.employee = employee;
    }
}
