package com.openerp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "sale_group")
@Getter
@Setter
@NoArgsConstructor
public class SaleGroup {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "sale_sequence")
    @SequenceGenerator(sequenceName = "aa_sale_sequence", allocationSize = 1, name = "sale_sequence")
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

    public SaleGroup(String name, String attr1, String attr2, DictionaryType dictionaryType) {
        this.name = name;
        this.attr1 = attr1;
        this.attr2 = attr2;
        this.dictionaryType = dictionaryType;
    }

    public SaleGroup(@Pattern(regexp = ".{2,50}", message = "Minimum 2 maksimum 50 simvol ola bilər") String name, @Pattern(regexp = ".{1,50}", message = "Minimum 1 maksimum 50 simvol ola bilər") String attr1) {
        this.name = name;
        this.attr1 = attr1;
    }
}
