package com.openerp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "admin_migration_detail_service_regulator")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class MigrationDetailServiceRegulator {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "admin_migration_detail_service_regulator_sequence")
    @SequenceGenerator(sequenceName = "aa_admin_migration_detail_service_regulator_sequence", allocationSize = 1, name = "admin_migration_detail_service_regulator_sequence")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "admin_migration_id")
    private Migration migration;

    @Column(name = "is_active", nullable = false, columnDefinition="boolean default true")
    private Boolean active = true;

    @Column(name = "status", nullable = false)
    private Integer status = 0;

    @ToString.Exclude
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sale_sales_id")
    private Sales sales;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @JsonFormat(pattern = "dd.MM.yyyy")
    @Column(name = "serviced_date")
    private Date servicedDate;

    @Column(name = "customer_full_name")
    private String customerFullName;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hr_employee_servicer_id")
    private Employee servicer;

    @Column(name = "employee_servicer")
    private String employeeServicer;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "admin_dictionary_service_notification_pambiq_id")
    private Dictionary pambiq;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "admin_dictionary_service_notification_komur_id")
    private Dictionary komur;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "admin_dictionary_service_notification_kristal_id")
    private Dictionary kristal;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "admin_dictionary_service_notification_membran_id")
    private Dictionary membran;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "admin_dictionary_service_notification_kakos_id")
    private Dictionary kakos;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "admin_dictionary_service_notification_mineral_id")
    private Dictionary mineral;

    @Column(name = "sales_payment_last_price")
    private Double salesPaymentLastPrice;

    @Column(name = "sales_payment_down")
    private Double salesPaymentDown;

    @Column(name = "sales_payment_payed")
    private Double salesPaymentPayed;

    @Column(name = "sales_saled")
    private Boolean salesSaled;

    @Column(name = "sales_payment_is_cash")
    private Boolean salesPaymentCash;

    @Column(name = "sales_payment_is_gift")
    private Boolean salesPaymentGift;

    @Column(name = "sales_inventory_names")
    private String salesInventoryNames;

    @Lob
    @Column(name = "errors")
    private String errors;
}
