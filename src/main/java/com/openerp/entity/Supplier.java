package com.openerp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "warehouse_supplier")
@Getter
@Setter
@NoArgsConstructor
public class Supplier {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "warehouse_sequence")
    @SequenceGenerator(sequenceName = "aa_warehouse_sequence", allocationSize = 1, name = "warehouse_sequence")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Pattern(regexp=".{2,50}",message="Minimum 2 maksimum 50 simvol ola bilər")
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Pattern(regexp=".{0,250}",message="Maksimum 250 simvol ola bilər")
    @Column(name = "description")
    private String description;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "common_person_id")
    private Person person;

    @Column(name = "is_active", nullable = false, columnDefinition="boolean default true")
    private Boolean active = true;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false)
    private Date createdDate = new Date();

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "created_by_admin_user_id")
    private User createdUser;

    public Supplier(@Pattern(regexp = ".{2,50}", message = "Minimum 2 maksimum 50 simvol ola bilər") String name, @Pattern(regexp = ".{0,250}", message = "Maksimum 250 simvol ola bilər") String description, Person person) {
        this.name = name;
        this.description = description;
        this.person = person;
    }
}
