package com.openerp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "global_configuration")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class GlobalConfiguration {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "global_configuration_seq")
    @SequenceGenerator(sequenceName = "global_configuration_seq", allocationSize = 1, name = "global_configuration_seq")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Pattern(regexp=".{2,50}",message="Minimum 2 maksimum 50 simvol ola bilər")
    @Column(name = "name", nullable = false)
    private String name;

    @Pattern(regexp=".{2,50}",message="Minimum 2 maksimum 50 simvol ola bilər")
    @Column(name = "key_field", unique = true, nullable = false)
    private String key;

    @Column(name = "attribute")
    private String attribute;

    @Pattern(regexp=".{0,250}",message="Maksimum 250 simvol ola bilər")
    @Column(name = "description")
    private String description;

    @Column(name = "is_active", nullable = false, columnDefinition="boolean default true")
    private Boolean active = true;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false)
    private Date createdDate = new Date();

    public GlobalConfiguration(String name, String key, String attribute, String description) {
        this.name = name;
        this.key = key;
        this.attribute = attribute;
        this.description = description;
    }
}
