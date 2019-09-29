package com.openerp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

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

    @Pattern(regexp=".{2,50}",message="Minimum 2 maksimum 50 simvol ola bilər")
    @Column(name = "name", nullable = false)
    private String name;

    @Pattern(regexp=".{1,50}",message="Minimum 1 maksimum 50 simvol ola bilər")
    @Column(name = "attr1")
    private String attr1;

    @Column(name = "attr2")
    private String attr2;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="admin_dictionary_type_id", nullable = false)
    private DictionaryType dictionaryType;

    @Column(name = "is_active", nullable = false, columnDefinition="boolean default true")
    private Boolean active = true;

    public Dictionary(String name, String attr1, String attr2, DictionaryType dictionaryType) {
        this.name = name;
        this.attr1 = attr1;
        this.attr2 = attr2;
        this.dictionaryType = dictionaryType;
    }
}
