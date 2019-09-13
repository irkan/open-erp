package com.openerp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "admin_dictionary")
@Getter
@Setter
@NoArgsConstructor
public class Dictionary {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "admin_sequence")
    @SequenceGenerator(sequenceName = "aa_admin_sequence", allocationSize = 1, name = "admin_sequence")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "attr1")
    private String attr1;

    @Column(name = "attr2")
    private String attr2;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="admin_dictionary_type_id", nullable = false)
    private DictionaryType dictionaryType;

    /*@JsonIgnore
    @OneToOne(mappedBy = "gender")
    private Person gender;

    @JsonIgnore
    @OneToOne(mappedBy = "nationality")
    private Person nationality;

    @JsonIgnore
    @OneToOne(mappedBy = "city")
    private PersonContact city1;

    @JsonIgnore
    @OneToOne(mappedBy = "documentType")
    private PersonDocument documentType;

    @JsonIgnore
    @OneToOne(mappedBy = "city")
    private OrganizationContact city2;

    @JsonIgnore
    @OneToOne(mappedBy = "icon")
    private Module icon1;

    @JsonIgnore
    @OneToOne(mappedBy = "icon")
    private Operation icon2;

    @JsonIgnore
    @OneToOne(mappedBy = "icon")
    private Organization icon3;

    @JsonIgnore
    @OneToOne(mappedBy = "position")
    private Employee position;*/

    @Column(name = "is_active", nullable = false, columnDefinition="boolean default true")
    private Boolean active = true;

    public Dictionary(String name, String attr1, String attr2, DictionaryType dictionaryType) {
        this.name = name;
        this.attr1 = attr1;
        this.attr2 = attr2;
        this.dictionaryType = dictionaryType;
    }
}
