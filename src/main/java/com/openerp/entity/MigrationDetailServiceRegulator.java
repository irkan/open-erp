package com.openerp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "admin_migration_detail")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class MigrationDetailServiceRegulator {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "admin_migration_detail_sequence")
    @SequenceGenerator(sequenceName = "aa_admin_migration_detail_sequence", allocationSize = 1, name = "admin_migration_detail_sequence")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "admin_migration_id")
    private Migration migration;

    @Column(name = "is_active", nullable = false, columnDefinition="boolean default true")
    private Boolean active = true;

    @Column(name = "status", nullable = false)
    private Integer status = 0;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @JsonFormat(pattern="dd.MM.yyyy")
    @Column(name = "sales_date")
    private Date salesDate;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "crm_customer_id")
    private Customer customer;

    @Lob
    @Column(name = "errors")
    private String errors;
}
