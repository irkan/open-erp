package com.openerp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "sale_sales")
@Getter
@Setter
@NoArgsConstructor
public class Sales {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "sale_sequence")
    @SequenceGenerator(sequenceName = "aa_sale_sequence", allocationSize = 1, name = "sale_sequence")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "crm_customer_id", nullable = false)
    private Customer customer;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "warehouse_action_id")
    private Action action;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "sale_payment_id", nullable = false)
    private Payment payment;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hr_employee_console_id")
    private Employee console;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hr_employee_van_leader_id")
    private Employee vanLeader;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hr_employee_dealer_id")
    private Employee dealer;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hr_employee_canavasser_id")
    private Employee canavasser;

    @Column(name = "is_active", nullable = false, columnDefinition="boolean default true")
    private Boolean active = true;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false)
    private Date createdDate = new Date();

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "created_by_admin_user_id")
    private User createdUser;
}
