package com.openerp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "admin_template_module_operation")
@Getter
@Setter
@NoArgsConstructor
public class TemplateModuleOperation {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "admin_sequence")
    @SequenceGenerator(sequenceName = "aa_admin_sequence", allocationSize = 1, name = "admin_sequence")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "admin_dictionary_template_id")
    private Dictionary template;

    @ManyToOne(fetch = FetchType.EAGER)
    private ModuleOperation moduleOperation;

    @OneToMany(fetch=FetchType.LAZY )
    @JoinColumn(name = "admin_module_operation_id")
    private List<ModuleOperation> moduleOperations;

    public TemplateModuleOperation(Dictionary template, ModuleOperation moduleOperation) {
        this.template = template;
        this.moduleOperation = moduleOperation;
    }
}
