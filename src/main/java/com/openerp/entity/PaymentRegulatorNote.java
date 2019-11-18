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
@Table(name = "collect_payment_regulator_note")
@Getter
@Setter
@NoArgsConstructor
public class PaymentRegulatorNote {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "collect_sequence")
    @SequenceGenerator(sequenceName = "aa_collect_sequence", allocationSize = 1, name = "collect_sequence")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sale_payment_id")
    private Payment payment;

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

    @Column(name = "is_active", nullable = false, columnDefinition="boolean default true")
    private Boolean active = true;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false)
    private Date createdDate = new Date();

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "created_by_admin_user_id")
    private User createdUser;

}
