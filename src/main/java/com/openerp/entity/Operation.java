package com.openerp.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "admin_operation")
@Getter
@Setter
@NoArgsConstructor
public class Operation {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "admin_sequence")
    @SequenceGenerator(sequenceName = "aa_admin_sequence", allocationSize = 1, name = "admin_sequence")
    @JsonProperty("id_input")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @JsonProperty("name_input")
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @JsonProperty("path_input")
    @Column(name = "path")
    private String path;

    @JsonProperty("icon_select")
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "admin_dictionary_icon_id")
    private Dictionary icon;

    @JsonIgnore
    @OneToMany(mappedBy = "operation")
    private List<ModuleOperation> moduleOperations;

    public Operation(String name, String path, Dictionary icon) {
        this.name = name;
        this.path = path;
        this.icon = icon;
    }
}
