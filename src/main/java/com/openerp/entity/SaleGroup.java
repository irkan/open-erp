package com.openerp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "sale_group")
@Getter
@Setter
@NoArgsConstructor
public class SaleGroup {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "sale_sequence")
    @SequenceGenerator(sequenceName = "aa_sale_sequence", allocationSize = 1, name = "sale_sequence")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Pattern(regexp=".{2,50}",message="Minimum 2 maksimum 50 simvol ola bilər")
    @Column(name = "name", nullable = false)
    private String name;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hr_organization_id")
    private Organization organization;

    @OneToMany(mappedBy = "saleGroup", cascade = CascadeType.ALL)
    private List<SaleGroupEmployee> saleGroupEmployees;

    @Column(name = "is_active", nullable = false, columnDefinition="boolean default true")
    private Boolean active = true;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false)
    private Date createdDate = new Date();

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "created_by_admin_user_id")
    private User createdUser;
}
