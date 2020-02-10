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

@Entity
@Table(name = "collect_contact_history")
@Getter
@Setter
@NoArgsConstructor
public class ContactHistory {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "collect_sequence")
    @SequenceGenerator(sequenceName = "aa_collect_sequence", allocationSize = 1, name = "collect_sequence")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hr_organization_id")
    private Organization organization;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sale_sales_id")
    private Sales sales;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "admin_dictionary_contact_channel_id")
    private Dictionary contactChannel;

    @Pattern(regexp=".{2,255}",message="Minimum 2 maksimum 255 simvol ola bilər")
    @Column(name = "description")
    private String description;

    @Temporal(TemporalType.DATE)
    @Column(name = "next_contact_date")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date nextContactDate;

    @Transient
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date nextContactDateFrom;

    @Column(name = "is_active", nullable = false, columnDefinition="boolean default true")
    private Boolean active = true;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false)
    private Date createdDate = new Date();

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "created_by_admin_user_id")
    private User createdUser;

    public ContactHistory(Sales sales, Organization organization) {
        this.sales = sales;
        this.organization = organization;
    }


}