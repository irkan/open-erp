package com.openerp.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "operation")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Operation {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "operation_seq")
    @SequenceGenerator(sequenceName = "operation_seq", allocationSize = 1, name = "operation_seq")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Pattern(regexp=".{2,50}",message="Minimum 2 maksimum 50 simvol ola bilər")
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "path")
    private String path;

    @Column(name = "icon")
    private String icon;

    @Column(name = "is_active", nullable = false, columnDefinition="boolean default true")
    private Boolean active = true;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "operation")
    private List<ModuleOperation> moduleOperations;

    public Operation(String name, String path, String icon) {
        this.name = name;
        this.path = path;
        this.icon = icon;
    }
}
