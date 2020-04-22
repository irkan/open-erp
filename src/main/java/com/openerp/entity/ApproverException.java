package com.openerp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Entity
@Table(name = "admin_approver_exception")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ApproverException {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "admin_sequence")
    @SequenceGenerator(sequenceName = "aa_admin_sequence", allocationSize = 1, name = "admin_sequence")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "warehouse_organization_id")
    private Organization organization;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "admin_user_id", unique = true, nullable = false)
    private User user;

    @Pattern(regexp=".{0,250}",message="Maksimum 250 simvol ola bilər")
    @Column(name = "description")
    private String description;

    public ApproverException(Organization organization) {
        this.organization = organization;
    }
}
