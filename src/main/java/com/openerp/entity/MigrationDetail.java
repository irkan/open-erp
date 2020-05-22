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
@Table(name = "admin_migration_detail")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class MigrationDetail {

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

    @Column(name = "customer_full_name")
    private String customerFullName;

    @Column(name = "customer_contact_address")
    private String customerContactAddress;

    @Column(name = "customer_contact_phone_numbers")
    private String customerContactPhoneNumbers;

    @Column(name = "employee_van_leader")
    private String employeeVanLeader;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hr_employee_van_leader_id")
    private Employee vanLeader;

    @Column(name = "employee_console")
    private String employeeConsole;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hr_employee_console_id")
    private Employee console;

    @Column(name = "employee_canvasser")
    private String employeeCanvasser;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hr_employee_canavasser_id")
    private Employee canvasser;

    @Column(name = "employee_dealer")
    private String employeeDealer;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hr_employee_dealer_id")
    private Employee dealer;

    @Column(name = "employee_servicer")
    private String employeeServicer;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hr_employee_servicer_id")
    private Employee servicer;

    @Column(name = "sales_payment_last_price")
    private Double salesPaymentLastPrice;

    @Column(name = "sales_payment_down")
    private Double salesPaymentDown;

    @Column(name = "sales_payment_payed")
    private Double salesPaymentPayed;

    @Column(name = "sales_payment_period")
    private Integer salesPaymentPeriod;

    @Column(name = "sales_payment_schedule")
    private Integer salesPaymentSchedule;

    @Column(name = "sales_payment_is_cash")
    private Boolean salesPaymentCash;

    @Column(name = "sales_payment_is_gift")
    private Boolean salesPaymentGift;

    @Column(name = "sales_inventory_name")
    private String salesInventoryName;
}