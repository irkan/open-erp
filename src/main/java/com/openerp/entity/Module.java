package com.openerp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "admin_module")
@Getter
@Setter
@NoArgsConstructor
public class Module {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "admin_sequence")
    @SequenceGenerator(sequenceName = "aa_admin_sequence", allocationSize = 1, name = "admin_sequence")
    @JsonProperty("id_input")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @JsonProperty("name_input")
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @JsonProperty("description_input")
    @Column(name = "description")
    private String description;

    @JsonProperty("path_input")
    @Column(name = "path")
    private String path;

    @JsonProperty("icon_select")
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "admin_dictionary_icon_id")
    private Dictionary icon;

    @JsonIgnore
    @OneToMany(mappedBy = "module")
    private List<ModuleOperation> moduleOperations;

    @JsonProperty("module_select")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "module_id", referencedColumnName = "id")
    private Module module;

    @JsonIgnore
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="module")
    private List<Module> children;

    public Module(String name, String description, String path, Dictionary icon, Module module) {
        this.name = name;
        this.description = description;
        this.path = path;
        this.icon = icon;
        this.module = module;
    }
}
