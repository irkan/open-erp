package com.openerp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "invoice")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Invoice {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "invoice_seq")
    @SequenceGenerator(sequenceName = "invoice_seq", initialValue = 100001, allocationSize = 1, name = "invoice_seq")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dictionary_payment_channel_id")
    private Dictionary paymentChannel;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sales_id")
    private Sales sales;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id")
    private Employee collector;

    @Column(name = "price", nullable = false, columnDefinition="Decimal(10,2) default 0")
    private Double price=0d;

    @ToString.Exclude
    @Transient
    private Double priceFrom;

    @Temporal(TemporalType.DATE)
    @Column(name = "invoice_date", nullable = false)
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date invoiceDate = new Date();

    @ToString.Exclude
    @Transient
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date invoiceDateFrom;

    @Lob
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

    @Column(name = "is_creditable", nullable = false, columnDefinition="boolean default true")
    private Boolean creditable = true;

    @Column(name = "is_active", nullable = false, columnDefinition="boolean default true")
    private Boolean active = true;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false)
    private Date createdDate = new Date();

    public Invoice(Organization organization) {
        this.organization = organization;
    }
    public Invoice(Sales sales, Organization organization) {
        this.sales = sales;
        this.organization = organization;
    }

    public Invoice(Organization organization, Sales sales, Date invoiceDate) {
        this.organization = organization;
        this.sales = sales;
        this.invoiceDate = invoiceDate;
    }

    public Invoice(Organization organization, Double price, Boolean approve, Date invoiceDate, Sales sales) {
        this.organization = organization;
        this.price = price;
        this.approve = approve;
        this.invoiceDate = invoiceDate;
        this.sales = sales;
    }

    public Invoice(Employee collector, Double price, Boolean approve) {
        this.collector = collector;
        this.price = price;
        this.approve = approve;
    }

    public Invoice(Double price, Boolean approve, Sales sales) {
        this.price = price;
        this.approve = approve;
        this.sales = sales;
    }
}
