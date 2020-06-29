package com.openerp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Entity
@Table(name = "demonstration")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Demonstration {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "demonstration_seq")
    @SequenceGenerator(sequenceName = "demonstration_seq", initialValue = 1, allocationSize = 1, name = "demonstration_seq")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @Column(name = "demonstrate_date", nullable = false)
    private Date demonstrateDate = new Date();

    @ToString.Exclude
    @Transient
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date demonstrateDateFrom;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Min(value = 1, message = "Minimum 1 olmalıdır!")
    @Column(name = "amount", nullable = false)
    private int amount=1;

    @Pattern(regexp=".{0,250}", message="Maksimum 250 simvol ola bilər")
    @Column(name = "description")
    private String description;

    @Column(name = "is_active", nullable = false, columnDefinition="boolean default true")
    private Boolean active = true;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false)
    private Date createdDate = new Date();

    public Demonstration(Organization organization) {
        this.organization = organization;
    }

    public Demonstration(Organization organization, Employee employee, Date demonstrateDate) {
        this.organization = organization;
        this.employee = employee;
        this.demonstrateDate = demonstrateDate;
    }
}
