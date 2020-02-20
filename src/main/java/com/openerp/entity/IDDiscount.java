package com.openerp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.openerp.validation.EmployeeGroup;
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
@Table(name = "idgroup_id_discount")
@Getter
@Setter
@NoArgsConstructor
public class IDDiscount {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "idgroup_sequence")
    @SequenceGenerator(sequenceName = "aa_idgroup_sequence", allocationSize = 1, name = "idgroup_sequence")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Pattern(regexp=".{1,255}",message="Minimum 1 maksimum 255 simvol ola bilər")
    @Column(name = "code", nullable = false)
    private String code;

    @DecimalMin(value = "0", message = "Minimum 0 olmalıdır")
    @Column(name = "discount", nullable = false, columnDefinition="Decimal(10,2) default 0")
    private Double discount=0d;

    @Pattern(regexp=".{0,255}",message="Maksimum 255 simvol ola bilər")
    @Column(name = "description")
    private String description;

    @Column(name = "is_active", nullable = false, columnDefinition="boolean default true")
    private Boolean active = true;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false)
    private Date createdDate = new Date();

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "created_by_admin_user_id")
    private User createdUser;
}
