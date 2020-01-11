package com.openerp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "sale_invoice")
@Getter
@Setter
@NoArgsConstructor
public class Invoice {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "invoice_sequence")
    @SequenceGenerator(sequenceName = "aa_invoice_sequence", initialValue = 100001, allocationSize = 1, name = "invoice_sequence")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hr_organization_id")
    private Organization organization;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "admin_dictionary_payment_channel_id")
    private Dictionary paymentChannel;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sale_sales_id")
    private Sales sales;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hr_employee_id")
    private Employee collector;

    @DecimalMin(value = "0", message = "Minimum 0 olmalıdır")
    @Column(name = "price", nullable = false, columnDefinition="Decimal(10,2) default 0")
    private Double price=0d;

    @Transient
    private Double priceFrom;

    @Temporal(TemporalType.DATE)
    @Column(name = "invoice_date", nullable = false)
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date invoiceDate = new Date();

    @Transient
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date invoiceDateFrom;

    @Pattern(regexp=".{0,250}", message="Maksimum 250 simvol ola bilər")
    @Column(name = "description")
    private String description;

    @Pattern(regexp=".{0,50}", message="Maksimum 50 simvol ola bilər")
    @Column(name = "channel_reference_code")
    private String channelReferenceCode;

    @Column(name = "is_approve", nullable = false, columnDefinition="boolean default true")
    private Boolean approve = true;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "approve_date")
    private Date approveDate;

    @Column(name = "is_advance", nullable = false, columnDefinition="boolean default false")
    private Boolean advance = false;

    @Column(name = "is_active", nullable = false, columnDefinition="boolean default true")
    private Boolean active = true;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false)
    private Date createdDate = new Date();

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "created_by_admin_user_id")
    private User createdUser;

    public Invoice(Organization organization) {
        this.organization = organization;
    }
}
