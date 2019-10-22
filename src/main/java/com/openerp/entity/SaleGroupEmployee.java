package com.openerp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Entity
@Table(name = "sale_group_employee")
@Getter
@Setter
@NoArgsConstructor
public class SaleGroupEmployee {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "sale_sequence")
    @SequenceGenerator(sequenceName = "aa_sale_sequence", allocationSize = 1, name = "sale_sequence")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "sale_group_id")
    private SaleGroup saleGroup;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="hr_employee_id", nullable = false)
    private Employee employee;

    @Column(name = "is_active", nullable = false, columnDefinition="boolean default true")
    private Boolean active = true;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false)
    private Date createdDate = new Date();

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "created_by_admin_user_id")
    private User createdUser;

    public SaleGroupEmployee(SaleGroup saleGroup, Employee employee) {
        this.saleGroup = saleGroup;
        this.employee = employee;
    }
}
