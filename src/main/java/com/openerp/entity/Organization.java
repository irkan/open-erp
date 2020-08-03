package com.openerp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.openerp.util.Util;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "organization")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Organization {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "organization_seq")
    @SequenceGenerator(sequenceName = "organization_seq", allocationSize = 1, name = "organization_seq")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Pattern(regexp=".{2,50}",message="Minimum 2 maksimum 50 simvol ola bilər")
    @Column(name = "name", nullable = false)
    private String name;

    @Pattern(regexp=".{0,255}",message="Maksimum 255 simvol ola bilər")
    @Column(name = "description")
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false)
    private Date createdDate = new Date();

    @Column(name = "is_active", nullable = false, columnDefinition="boolean default true")
    private Boolean active = true;

    @ToString.Exclude
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "organization_id", referencedColumnName = "id")
    private Organization organization;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="organization")
    private List<Organization> children;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dictionary_org_type_id")
    private Dictionary type;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "contact_id")
    private Contact contact;

    public String getReportCondition() {
        return " and o.id=" + this.id + " ";
    }

    public Organization(Contact contact, String name, String description, Organization organization, Dictionary type) {
        this.contact = contact;
        this.name = name;
        this.description = description;
        this.organization = organization;
        this.type = type;
    }

    public Organization(@Pattern(regexp = ".{2,50}", message = "Minimum 2 maksimum 50 simvol ola bilər") String name, @Pattern(regexp = ".{0,255}", message = "Maksimum 255 simvol ola bilər") String description) {
        this.name = name;
        this.description = description;
    }
}
