package com.openerp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Entity
@Table(name = "payroll_configuration")
@Getter
@Setter
@NoArgsConstructor
public class PayrollConfiguration {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "payroll_sequence")
    @SequenceGenerator(sequenceName = "aa_payroll_sequence", allocationSize = 1, name = "payroll_sequence")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Pattern(regexp=".{2,100}",message="Minimum 2 maksimum 100 simvol ola bilər")
    @Column(name = "name")
    private String name;

    @Pattern(regexp=".{2,255}",message="Minimum 2 maksimum 255 simvol ola bilər")
    @Column(name = "formula")
    private String formula;

    @Pattern(regexp=".{0,255}",message="Maksimum 255 simvol ola bilər")
    @Column(name = "description")
    private String description;

    @Column(name = "is_active", nullable = false, columnDefinition="boolean default true")
    private Boolean active = true;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false)
    private Date createdDate = new Date();

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "created_by_admin_user_id")
    private User createdUser;

    public PayrollConfiguration(@Pattern(regexp = ".{2,100}", message = "Minimum 2 maksimum 100 simvol ola bilər") String name, @Pattern(regexp = ".{2,300}", message = "Minimum 2 maksimum 300 simvol ola bilər") String formula, @Pattern(regexp = ".{0,400}", message = "Maksimum 400 simvol ola bilər") String description) {
        this.name = name;
        this.formula = formula;
        this.description = description;
    }
}
