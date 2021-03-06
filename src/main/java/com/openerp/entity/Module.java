package com.openerp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.openerp.util.Util;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "module")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Module {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "module_seq")
    @SequenceGenerator(sequenceName = "module_seq", allocationSize = 1, name = "module_seq")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Pattern(regexp=".{2,50}",message="Minimum 2 maksimum 50 simvol ola bilər")
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "path")
    private String path;

    @Column(name = "icon")
    private String icon;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "module")
    private List<ModuleOperation> moduleOperations;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "module_id", referencedColumnName = "id")
    private Module module;

    @Column(name = "is_active", nullable = false, columnDefinition="boolean default true")
    private Boolean active = true;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="module")
    private List<Module> children;

    public String getLabel() {
        String label = "";
        if(module!=null && module.getName()!=null){
            label=" - " + module.getName();
        }
        return name+label;
    }

    public Module(String name, String description, String path, String icon, Module module) {
        this.name = name;
        this.description = description;
        this.path = path;
        this.icon = icon;
        this.module = module;
    }
}
