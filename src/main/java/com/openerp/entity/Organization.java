package com.openerp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "hr_organization")
@Getter
@Setter
@NoArgsConstructor
public class Organization implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "hr_sequence")
    @SequenceGenerator(sequenceName = "aa_hr_sequence", allocationSize = 1, name = "hr_sequence")
    @JsonProperty("id_input")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @JsonProperty("name_input")
    @Column(name = "name", nullable = false)
    private String name;

    @JsonProperty("description_input")
    @Column(name = "description")
    private String description;

    @JsonProperty("createdDate_input")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false)
    private Date createdDate = new Date();

    @JsonProperty("active_input")
    @Column(name = "is_active", nullable = false, columnDefinition="boolean default true")
    private Boolean active = true;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "organization_id", referencedColumnName = "id")
    private Organization organization;

    @JsonIgnore
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="organization")
    private List<Organization> children;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "common_contact_id")
    private Contact contact;

    public Organization(Contact contact, String name, String description, Organization organization) {
        this.contact = contact;
        this.name = name;
        this.description = description;
        this.organization = organization;
    }
}
